package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import com.example.geektrust.domain.internal.Ride;
import com.example.geektrust.services.BillService;
import com.example.geektrust.services.DriverService;
import com.example.geektrust.services.RideService;
import com.example.geektrust.services.RiderService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    if (driversWithinRange.isEmpty()) {
      return;
    }
    if (nthDriver > driversWithinRange.size()) {
      System.out.println(INVALID_RIDE);
      return;
    }

    String selectedDriverId =
        driversWithinRange.keySet().stream().skip(nthDriver - 1).findFirst().orElse(null);
    if (selectedDriverId != null) {
      Driver selectedDriver = driversWithinRange.get(selectedDriverId);
      selectedDriver.updateAvailability(false);
      rides.put(rideId, new Ride(rideId, riderId, selectedDriverId));
      System.out.println(RIDE_STARTED + SPACE + rideId);
    } else {
      System.out.println(NO_DRIVERS_AVAILABLE);
    }
  }

  @Override
  public void endRide(String rideId, Coordinates endCoordinates, Long rideTime) {
    if (rides.get(rideId) == null) {
      System.out.println(INVALID_RIDE);
      return;
    }
    Ride ride = rides.get(rideId);
    String riderId = (String) ride.getRideDetails(rideId).get(KEY_RIDER_ID);
    Boolean isRideCompleted = (Boolean) ride.getRideDetails(rideId).get(KEY_RIDE_COMPLETED);
    if (isRideCompleted) {
      System.out.println(INVALID_RIDE);
      return;
    }

    Coordinates startCoordinates = riderService.getRiderCoordinates(riderId);
    BigDecimal rideDistance = getDistanceBetweenRiderAndDriver(startCoordinates, endCoordinates);
    BigDecimal rideBill = billService.getRideBill(rideDistance, rideTime);
    ride.completeRide(rideBill, rideTime, rideDistance);
    riderService.updateRiderCoordinates(riderId, endCoordinates);
    driverService.updateDriverCoordinates(
        (String) ride.getRideDetails(rideId).get(KEY_DRIVER_ID), endCoordinates);
    System.out.println(RIDE_STOPPED + SPACE + rideId);
  }

  @Override
  public Map<String, Driver> getDriverWithinRange(String riderId) {
    Map<String, Driver> allDrivers = driverService.getAllDrivers();
    Map<String, Driver> nearestDrivers = new HashMap<>();
    Coordinates riderCoordinates = riderService.getRiderCoordinates(riderId);
    if (allDrivers.isEmpty()) {
      System.out.println(NO_DRIVERS_AVAILABLE);
    } else {
      for (Map.Entry<String, Driver> entry : allDrivers.entrySet()) {
        Driver driver = entry.getValue();
        Coordinates driverCoordinates = driver.getCoordinates();
        BigDecimal distance = getDistanceBetweenRiderAndDriver(riderCoordinates, driverCoordinates);
        if (distance.compareTo(BigDecimal.valueOf(MAX_ALLOWED_DISTANCE)) <= 0) {
          nearestDrivers.put(entry.getKey(), driver);
        }
      }
    }
    return nearestDrivers;
  }

  private BigDecimal getDistanceBetweenRiderAndDriver(
      Coordinates riderCoordinates, Coordinates driverCoordinates) {
    Long x1 = riderCoordinates.getXCoordinate();
    Long y1 = riderCoordinates.getYCoordinate();
    Long x2 = driverCoordinates.getXCoordinate();
    Long y2 = driverCoordinates.getYCoordinate();
    return BigDecimal.valueOf(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
  }

  @Override
  public void printMatchedDrivers(String riderId) {
    Map<String, Driver> driversWithinRange = getDriverWithinRange(riderId);
    if (driversWithinRange.isEmpty()) {
      System.out.println(NO_DRIVERS_AVAILABLE);
    } else {
      System.out.print(DRIVERS_MATCHED + SPACE + SPACE);
      driversWithinRange.forEach(
          (driverId, driver) -> {
            System.out.print(driverId + SPACE);
          });
      System.out.println(SPACE);
    }
  }

  @Override
  public void getRideBill(String rideId) {
    Ride ride = rides.get(rideId);
    String driverId = (String) ride.getRideDetails(rideId).get(KEY_DRIVER_ID);
    BigDecimal rideBill =
        billService.getRideBill(
            (BigDecimal) ride.getRideDetails(rideId).get(KEY_RIDE_DISTANCE),
            (Long) ride.getRideDetails(rideId).get(KEY_RIDE_TIME));
    System.out.println(GET_BILL_COMMAND + SPACE + rideId + SPACE + driverId + SPACE + rideBill);
  }
}
