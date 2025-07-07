package com.example.geektrust.enums;

public enum DriverAvailability {
  AVAILABLE(true),
  UNAVAILABLE(false);

  private final boolean isAvailable;

  DriverAvailability(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }
}
