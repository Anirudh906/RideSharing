package com.example.geektrust.services;

import java.math.BigDecimal;

public interface BillService {
  BigDecimal generateBill(String rideId);
}
