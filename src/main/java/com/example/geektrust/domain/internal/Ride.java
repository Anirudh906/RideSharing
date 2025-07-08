package com.example.geektrust.domain.internal;

import static com.example.geektrust.constants.CommonConstants.*;

import java.math.BigDecimal;

public class Ride {
  private final String rideId;
  private final String riderId;
  private final String driverId;
  private BigDecimal rideBill;
  private Long rideTime;
  private BigDecimal rideDistanceInKm;
  private Boolean isRideCompleted;

  public Ride(String rideId, String riderId, String driverId) {
    this.rideId = rideId;
    this.riderId = riderId;
    this.driverId = driverId;
    this.rideBill = BigDecimal.ZERO;
    this.rideTime = INITIAL_RIDE_TIME;
    this.rideDistanceInKm = BigDecimal.ZERO;
    this.isRideCompleted = false;
  }

  public void completeRide(BigDecimal rideBill, Long rideTime, BigDecimal rideDistanceInKm) {
    this.rideBill = rideBill;
    this.rideTime = rideTime;
    this.rideDistanceInKm = rideDistanceInKm;
    this.isRideCompleted = true;
  }

  public RideDetails getRideDetails() {
    return new RideDetails(
        rideId, riderId, driverId, rideBill, rideTime, rideDistanceInKm, isRideCompleted);
  }
}
