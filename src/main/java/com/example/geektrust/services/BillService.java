package com.example.geektrust.services;

import java.math.BigDecimal;

public interface BillService {
  BigDecimal getRideBill(BigDecimal rideDistance, Long rideTime);
}
