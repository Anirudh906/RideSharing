package com.example.geektrust.services.impl;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import com.example.geektrust.exceptions.DriverDoesntExistException;
import com.example.geektrust.services.DriverService;
import java.util.HashMap;
import java.util.Map;

public class DriverServiceImpl implements DriverService {
  private final Map<String, Driver> drivers;

  public DriverServiceImpl() {
    this.drivers = new HashMap<>();
  }

  @Override
  public void addDriver(String driverId, Long xCoordinate, Long yCoordinate) {
    Driver driver = new Driver(driverId, xCoordinate, yCoordinate);
    drivers.put(driverId, driver);
  }

  @Override
  public Coordinates getDriverCoordinates(String driverId) {
    if (!drivers.containsKey(driverId)) {
      throw new DriverDoesntExistException("Driver with ID " + driverId + " does not exist.");
    }
    return drivers.get(driverId).getCoordinates();
  }

  @Override
  public void updateDriverAvailability(String driverId, Boolean availability) {
    if (!drivers.containsKey(driverId)) {
      throw new DriverDoesntExistException("Driver with ID " + driverId + " does not exist.");
    }
    drivers.get(driverId).updateAvailability(availability);
  }

  @Override
  public Map<String, Driver> getAllDrivers() {
    Map<String, Driver> allDrivers = new HashMap<>();
    for (Map.Entry<String, Driver> entry : drivers.entrySet()) {
      Driver driver = entry.getValue();
      if (driver.getDriverAvailability()) {
        allDrivers.put(entry.getKey(), driver);
      }
    }
    return allDrivers;
  }

  @Override
  public void updateDriverCoordinates(String driverId, Coordinates coordinates) {
    if (!drivers.containsKey(driverId)) {
      throw new DriverDoesntExistException("Driver with ID " + driverId + " does not exist.");
    }
    drivers.get(driverId).updateCoordinates(coordinates);
  }
}
