package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import com.example.geektrust.services.DriverService;
import com.example.geektrust.services.RideService;
import com.example.geektrust.services.RiderService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RideServiceImpl implements RideService {

  private final DriverService driverService;
  private final RiderService riderService;

  public RideServiceImpl(DriverService driverService, RiderService riderService) {
    this.driverService = driverService;
    this.riderService = riderService;
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
      riderService.addRider(
          riderId,
          selectedDriver.getCoordinates().getXCoordinate(),
          selectedDriver.getCoordinates().getYCoordinate());
      driverService.markDriverUnavailable(selectedDriverId);
      System.out.println("Ride started with driver: " + selectedDriverId);
    } else {
      System.out.println("No valid driver found for the given selection.");
    }
  }

  @Override
  public void endRide(String riderId, String driverId) {}

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

  @Override
  public BigDecimal generateRideBil(String rideId) {
    return null;
  }

  @Override
  public void addRider(String riderId, Long xCoordinate, Long yCoordinate) {}

  @Override
  public Boolean isRiderRiding(String riderId) {
    return null;
  }

  private BigDecimal getDistanceBetweenRiderAndDriver(
      Coordinates riderCoordinates, Coordinates driverCoordinates) {
    Long x1 = riderCoordinates.getXCoordinate();
    Long y1 = riderCoordinates.getYCoordinate();
    Long x2 = driverCoordinates.getXCoordinate();
    Long y2 = driverCoordinates.getYCoordinate();
    return BigDecimal.valueOf(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
  }
}
