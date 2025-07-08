package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.domain.internal.*;
import com.example.geektrust.services.*;
import java.math.BigDecimal;
import java.util.*;

public class RideServiceImpl implements RideService {
  private final Map<String, Ride> rides;
  private final DriverService driverService;
  private final RiderService riderService;
  private final BillService billService;

  public RideServiceImpl(
      DriverService driverService, RiderService riderService, BillService billService) {
    this.driverService = driverService;
    this.riderService = riderService;
    this.billService = billService;
    this.rides = new HashMap<>();
  }

  @Override
  public void startRide(String rideId, Integer nthDriver, String riderId) {
    Map<String, Driver> driversWithinRange = getDriverWithinRange(riderId);
    if (!isValidRideStart(driversWithinRange, nthDriver)) {
      System.out.println(INVALID_RIDE);
      return;
    }
    String selectedDriverId = selectDriverId(driversWithinRange, nthDriver);
    if (selectedDriverId != null) {
      assignDriverToRide(rideId, riderId, selectedDriverId, driversWithinRange);
    } else {
      System.out.println(NO_DRIVERS_AVAILABLE);
    }
  }

  @Override
  public void endRide(String rideId, Coordinates endCoordinates, Long rideTime) {
    Ride ride = rides.get(rideId);
    if (!isValidRideEnd(ride)) {
      System.out.println(INVALID_RIDE);
      return;
    }
    RideDetails rideDetails = buildRideDetailsForCompletion(ride, endCoordinates, rideTime);
    ride.completeRide(rideDetails);
    updateCoordinatesAfterRide(ride, endCoordinates);
    System.out.println(RIDE_STOPPED + SPACE + rideId);
  }

  @Override
  public Map<String, Driver> getDriverWithinRange(String riderId) {
    Map<String, Driver> allDrivers = driverService.getAllDrivers();
    if (allDrivers.isEmpty()) return Collections.emptyMap();
    Coordinates riderCoordinates = riderService.getRiderCoordinates(riderId);
    PriorityQueue<DriverWithDistance> nearestDrivers =
        getNearestDriversQueue(allDrivers, riderCoordinates);
    return buildNearestDriversMap(nearestDrivers);
  }

  @Override
  public void printMatchedDrivers(String riderId) {
    Map<String, Driver> driversWithinRange = getDriverWithinRange(riderId);
    if (driversWithinRange.isEmpty()) {
      System.out.println(NO_DRIVERS_AVAILABLE);
    } else {
      System.out.print(DRIVERS_MATCHED + SPACE + SPACE);
      driversWithinRange.keySet().forEach(driverId -> System.out.print(driverId + SPACE));
      System.out.println(SPACE);
    }
  }

  @Override
  public void getRideBill(String rideId) {
    Ride ride = rides.get(rideId);
    if (ride == null) {
      System.out.println(INVALID_RIDE);
      return;
    }
    RideDetails details = ride.getRideDetails();
    BigDecimal rideBill =
        billService.getRideBill(details.getRideDistanceInKm(), details.getRideTime());
    System.out.println(
        GET_BILL_COMMAND + SPACE + rideId + SPACE + details.getDriverId() + SPACE + rideBill);
  }

  private boolean isValidRideStart(Map<String, Driver> driversWithinRange, Integer nthDriver) {
    return !driversWithinRange.isEmpty() && nthDriver <= driversWithinRange.size();
  }

  private String selectDriverId(Map<String, Driver> driversWithinRange, Integer nthDriver) {
    return driversWithinRange.keySet().stream()
        .skip(nthDriver - DRIVER_INDEX_OFFSET)
        .findFirst()
        .orElse(null);
  }

  private void assignDriverToRide(
      String rideId, String riderId, String driverId, Map<String, Driver> driversWithinRange) {
    Driver selectedDriver = driversWithinRange.get(driverId);
    selectedDriver.updateAvailability(false);
    rides.put(rideId, new Ride(rideId, riderId, driverId));
    System.out.println(RIDE_STARTED + SPACE + rideId);
  }

  private boolean isValidRideEnd(Ride ride) {
    return ride != null && !ride.getRideDetails().getIsRideCompleted();
  }

  private RideDetails buildRideDetailsForCompletion(
      Ride ride, Coordinates endCoordinates, Long rideTime) {
    String riderId = ride.getRideDetails().getRiderId();
    Coordinates startCoordinates = riderService.getRiderCoordinates(riderId);
    BigDecimal rideDistance = getDistanceBetweenCoordinates(startCoordinates, endCoordinates);
    BigDecimal rideBill = billService.getRideBill(rideDistance, rideTime);
    return new RideDetails(
        ride.getRideDetails().getRideId(),
        riderId,
        ride.getRideDetails().getDriverId(),
        rideBill,
        rideTime,
        rideDistance,
        true);
  }

  private void updateCoordinatesAfterRide(Ride ride, Coordinates endCoordinates) {
    String riderId = ride.getRideDetails().getRiderId();
    String driverId = ride.getRideDetails().getDriverId();
    riderService.updateRiderCoordinates(riderId, endCoordinates);
    driverService.updateDriverCoordinates(driverId, endCoordinates);
  }

  private PriorityQueue<DriverWithDistance> getNearestDriversQueue(
      Map<String, Driver> allDrivers, Coordinates riderCoordinates) {
    PriorityQueue<DriverWithDistance> nearestDrivers =
        new PriorityQueue<>(
            Comparator.comparing(DriverWithDistance::getDistance)
                .thenComparing(DriverWithDistance::getDriverId));
    for (Map.Entry<String, Driver> entry : allDrivers.entrySet()) {
      Driver driver = entry.getValue();
      Coordinates driverCoordinates = driver.getCoordinates();
      BigDecimal distance = getDistanceBetweenCoordinates(riderCoordinates, driverCoordinates);
      if (distance.compareTo(BigDecimal.valueOf(MAX_ALLOWED_DISTANCE))
          <= COMPARISON_EQUAL_OR_LESS) {
        nearestDrivers.offer(new DriverWithDistance(entry.getKey(), driver, distance));
      }
    }
    return nearestDrivers;
  }

  private Map<String, Driver> buildNearestDriversMap(
      PriorityQueue<DriverWithDistance> nearestDrivers) {
    Map<String, Driver> nearestDriversMap = new LinkedHashMap<>();
    while (!nearestDrivers.isEmpty()) {
      DriverWithDistance driverWithDistance = nearestDrivers.poll();
      nearestDriversMap.put(driverWithDistance.getDriverId(), driverWithDistance.getDriver());
    }
    return nearestDriversMap;
  }

  private BigDecimal getDistanceBetweenCoordinates(Coordinates c1, Coordinates c2) {
    Long x1 = c1.getXCoordinate();
    Long y1 = c1.getYCoordinate();
    Long x2 = c2.getXCoordinate();
    Long y2 = c2.getYCoordinate();
    return BigDecimal.valueOf(
        Math.sqrt(Math.pow(x2 - x1, SQUARE_EXPONENT) + Math.pow(y2 - y1, SQUARE_EXPONENT)));
  }
}
