package com.example.geektrust.domain.internal;

public class Rider {
  private String id;
  private Coordinates riderCoordinates;
  private Boolean isRiding;

  public Rider(String id, Long xCoordinate, Long yCoordinate) {
    this.id = id;
    this.riderCoordinates = new Coordinates(xCoordinate, yCoordinate);
    this.isRiding = false;
  }

  public Boolean isRiderRiding() {
    return isRiding;
  }

  public Coordinates getCoordinates() {
    return riderCoordinates;
  }

  public void updateCoordinates(Coordinates newCoordinates) {
    this.riderCoordinates = newCoordinates;
  }
}
