package com.example.geektrust.domain.internal;

public class Driver {
  private String driverId;
  private Coordinates driverCoordinates;
  private Boolean isAvailable;

  public Driver(String driverId, Long xCoordinate, Long yCoordinate) {
    this.driverId = driverId;
    this.driverCoordinates = new Coordinates(xCoordinate, yCoordinate);
    this.isAvailable = true;
  }

  public Boolean getDriverAvailability() {
    return isAvailable;
  }

  public void updateAvailability(Boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public void updateCoordinates(Long xCoordinate, Long yCoordinate) {
    this.driverCoordinates.setXCoordinate(xCoordinate);
    this.driverCoordinates.setYCoordinate(yCoordinate);
  }

  public Coordinates getCoordinates() {
    return driverCoordinates;
  }
}
