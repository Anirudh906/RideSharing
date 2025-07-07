package com.example.geektrust.services.impl;

import static com.example.geektrust.constants.CommonConstants.BASE_FARE;
import static com.example.geektrust.constants.CommonConstants.FARE_PER_KM;
import static com.example.geektrust.constants.CommonConstants.FARE_PER_MINUTE;
import static com.example.geektrust.constants.CommonConstants.ROUND_SCALE;
import static com.example.geektrust.constants.CommonConstants.SERVICE_TAX_MULTIPLIER;

import com.example.geektrust.services.BillService;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillServiceImpl implements BillService {

  @Override
  public BigDecimal getRideBill(BigDecimal rideDistance, Long rideTime) {
    BigDecimal distanceFare = rideDistance.multiply(FARE_PER_KM);
    BigDecimal timeFare = BigDecimal.valueOf(rideTime).multiply(FARE_PER_MINUTE);
    BigDecimal totalFare = distanceFare.add(timeFare).add(BigDecimal.valueOf(BASE_FARE));
    BigDecimal fareWithServiceTax = totalFare.multiply(SERVICE_TAX_MULTIPLIER);
    return fareWithServiceTax.setScale(ROUND_SCALE, RoundingMode.CEILING);
  }
}
