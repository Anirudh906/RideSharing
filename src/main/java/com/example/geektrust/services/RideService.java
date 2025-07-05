package com.example.geektrust.services;

import com.example.geektrust.domain.internal.Driver;
import java.math.BigDecimal;
import java.util.Map;

public interface RideService {
  void startRide(String rideId, Integer nthDriver, String riderId);

  void endRide(String riderId, String driverId);

  Map<String, Driver> getDriverWithinRange(String riderId);

  BigDecimal generateRideBil(String rideId);

  void addRider(String riderId, Long xCoordinate, Long yCoordinate);

  Boolean isRiderRiding(String riderId);
}
