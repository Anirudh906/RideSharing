package com.example.geektrust.domain.internal;

import static com.example.geektrust.constants.CommonConstants.KEY_DRIVER_ID;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDER_ID;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDE_BILL;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDE_COMPLETED;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDE_DISTANCE;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDE_ID;
import static com.example.geektrust.constants.CommonConstants.KEY_RIDE_TIME;

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
  private Boolean isRideCompleted;

  public Ride(String rideId, String riderId, String driverId) {
    this.rideId = rideId;
    this.riderId = riderId;
    this.driverId = driverId;
    this.rideBill = BigDecimal.ZERO;
    this.rideTime = 0L;
    this.rideDistanceInKm = BigDecimal.ZERO;
    this.isRideCompleted = false;
  }

  public Map<String, Object> getRideDetails(String rideId) {
    Map<String, Object> rideDetails = new HashMap<>();
    rideDetails.put(KEY_RIDE_ID, rideId);
    rideDetails.put(KEY_RIDER_ID, riderId);
    rideDetails.put(KEY_DRIVER_ID, driverId);
    rideDetails.put(KEY_RIDE_BILL, rideBill);
    rideDetails.put(KEY_RIDE_TIME, rideTime);
    rideDetails.put(KEY_RIDE_DISTANCE, rideDistanceInKm);
    rideDetails.put(KEY_RIDE_COMPLETED, isRideCompleted);
    return rideDetails;
  }

  public void completeRide(BigDecimal rideBill, Long rideTime, BigDecimal rideDistanceInKm) {
    this.rideBill = rideBill;
    this.rideTime = rideTime;
    this.rideDistanceInKm = rideDistanceInKm;
    this.isRideCompleted = true;
  }
}
