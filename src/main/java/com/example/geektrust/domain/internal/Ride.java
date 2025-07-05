package com.example.geektrust.domain.internal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Ride {
  private String rideId;
  private String riderId;
  private String driverId;
  private BigDecimal rideBill;
  private Long rideTime;
  private BigDecimal rideDistanceInKm;

  public Ride(String rideId, String riderId, String driverId) {
    this.rideId = rideId;
    this.riderId = riderId;
    this.driverId = driverId;
    this.rideBill = BigDecimal.ZERO;
    this.rideTime = 0L;
    this.rideDistanceInKm = BigDecimal.ZERO;
  }

  public Map<String, Object> getRideDetails(String rideId) {
    Map<String, Object> rideDetails = new HashMap<>();
    rideDetails.put("rideId", rideId);
    rideDetails.put("riderId", riderId);
    rideDetails.put("driverId", driverId);
    rideDetails.put("rideBill", rideBill);
    rideDetails.put("rideTime", rideTime);
    rideDetails.put("rideDistanceInKm", rideDistanceInKm);
    return rideDetails;
  }
}
