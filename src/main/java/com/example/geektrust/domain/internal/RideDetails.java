package com.example.geektrust.domain.internal;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RideDetails {
  private final String rideId;
  private final String riderId;
  private final String driverId;
  private final Long rideTime;
  private final BigDecimal rideDistanceInKm;
  private final Boolean isRideCompleted;
}
