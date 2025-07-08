package com.example.geektrust.domain.internal;

import java.math.BigDecimal;

public class DriverWithDistance {
  private final String driverId;
  private final Driver driver;
  private final BigDecimal distance;

  public DriverWithDistance(String driverId, Driver driver, BigDecimal distance) {
    this.driverId = driverId;
    this.driver = driver;
    this.distance = distance;
  }

  public String getDriverId() {
    return driverId;
  }

  public Driver getDriver() {
    return driver;
  }

  public BigDecimal getDistance() {
    return distance;
  }
}
