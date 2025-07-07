package com.example.geektrust.services;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import java.util.Map;

public interface RideService {
  void startRide(String rideId, Integer nthDriver, String riderId);

  void endRide(String rideId, Coordinates endCoordinates, Long rideTime);

  Map<String, Driver> getDriverWithinRange(String riderId);

  void printMatchedDrivers(String riderId);

  void getRideBill(String rideId);
}
