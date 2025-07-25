package com.example.geektrust.services;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import java.util.Map;

public interface DriverService {
  void addDriver(String driverId, Long xCoordinate, Long yCoordinate);

  Coordinates getDriverCoordinates(String driverId);

  void updateDriverAvailability(String driverId, Boolean availability);

  Map<String, Driver> getAllDrivers();

  void updateDriverCoordinates(String driverId, Coordinates coordinates);
}
