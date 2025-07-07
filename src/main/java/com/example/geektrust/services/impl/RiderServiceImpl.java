package com.example.geektrust.services.impl;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Rider;
import com.example.geektrust.exceptions.RiderDoesntExistException;
import com.example.geektrust.services.RiderService;
import java.util.HashMap;
import java.util.Map;

public class RiderServiceImpl implements RiderService {

  private final Map<String, Rider> riders;

  public RiderServiceImpl() {
    this.riders = new HashMap<>();
  }

  @Override
  public void addRider(String riderId, Long xCoordinate, Long yCoordinate) {
    Rider rider = new Rider(riderId, xCoordinate, yCoordinate);
    riders.put(riderId, rider);
  }

  @Override
  public Boolean isRiderRiding(String riderId) {
    if (!riders.containsKey(riderId)) {
      throw new RiderDoesntExistException("Rider with ID " + riderId + " does not exist.");
    }
    return riders.get(riderId).isRiderRiding();
  }

  @Override
  public Coordinates getRiderCoordinates(String riderId) {
    if (!riders.containsKey(riderId)) {
      throw new RiderDoesntExistException("Rider with ID " + riderId + " does not exist.");
    }
    return riders.get(riderId).getCoordinates();
  }

  @Override
  public void updateRiderCoordinates(String riderId, Coordinates endCoordinates) {
    if (!riders.containsKey(riderId)) {
      throw new RiderDoesntExistException("Rider with ID " + riderId + " does not exist.");
    }
    riders.get(riderId).updateCoordinates(endCoordinates);
  }
}
