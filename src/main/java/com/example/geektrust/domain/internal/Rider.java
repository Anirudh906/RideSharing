package com.example.geektrust.domain.internal;

import lombok.Getter;

public class Rider {
  @Getter private String id;
  private Coordinates riderCoordinates;

  public Rider(String id, Long xCoordinate, Long yCoordinate) {
    this.id = id;
    this.riderCoordinates = new Coordinates(xCoordinate, yCoordinate);
  }

  public Coordinates getCoordinates() {
    return new Coordinates(riderCoordinates.getXCoordinate(), riderCoordinates.getYCoordinate());
  }

  public void updateCoordinates(Coordinates newCoordinates) {
    if (newCoordinates == null) {
      throw new IllegalArgumentException("New coordinates cannot be null");
    }
    this.riderCoordinates =
        new Coordinates(newCoordinates.getXCoordinate(), newCoordinates.getYCoordinate());
  }
}
