package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.services.BillService;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillServiceImpl implements BillService {

  @Override
  public BigDecimal getRideBill(BigDecimal rideDistance, Long rideTime) {
    rideDistance = rideDistance.setScale(ROUND_SCALE_TWO, RoundingMode.HALF_UP);

    BigDecimal distanceFare = getDistanceFare(rideDistance);
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
