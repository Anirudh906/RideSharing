package com.example.geektrust.services;

import com.example.geektrust.domain.internal.Coordinates;

public interface RiderService {
  Coordinates getRiderCoordinates(String riderId);

  void addRider(String riderId, Long xCoordinate, Long yCoordinate);

  Boolean isRiderRiding(String riderId);

  void updateRiderCoordinates(String riderId, Coordinates endCoordinates);
}
