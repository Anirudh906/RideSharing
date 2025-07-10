package com.example.geektrust.domain.internal;

import static com.example.geektrust.constants.CommonConstants.*;
import static com.example.geektrust.constants.CommonConstants.ROUND_SCALE_TWO;
import static com.example.geektrust.constants.CommonConstants.SERVICE_TAX_MULTIPLIER;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

  public void completeRide(RideDetails details) {
    this.rideTime = details.getRideTime();
    this.rideDistanceInKm = details.getRideDistanceInKm();
    this.isRideCompleted = details.getIsRideCompleted();
  }

  public void updateRideBill(BigDecimal rideBill) {
    this.rideBill = rideBill;
  }

  public RideDetails getRideDetails() {
    return new RideDetails(rideId, riderId, driverId, rideTime, rideDistanceInKm, isRideCompleted);
  }

  public BigDecimal calculateBill() {
    this.rideDistanceInKm = this.rideDistanceInKm.setScale(ROUND_SCALE_TWO, RoundingMode.HALF_UP);

    BigDecimal distanceFare = getDistanceFare(this.rideDistanceInKm);
    BigDecimal timeFare = getTimeFare(rideTime);
    BigDecimal totalFare = distanceFare.add(timeFare).add(BigDecimal.valueOf(BASE_FARE));

    return totalFare
        .multiply(SERVICE_TAX_MULTIPLIER)
        .setScale(ROUND_SCALE_TWO, RoundingMode.HALF_UP);
  }

  private BigDecimal getDistanceFare(BigDecimal rideDistance) {
    return rideDistance.multiply(FARE_PER_KM).setScale(ROUND_SCALE_TWO, RoundingMode.HALF_UP);
  }

  private BigDecimal getTimeFare(Long rideTime) {
    return BigDecimal.valueOf(rideTime)
        .multiply(FARE_PER_MINUTE)
        .setScale(ROUND_SCALE_TWO, RoundingMode.HALF_UP);
  }
}
