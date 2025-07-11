package com.example.geektrust.controller;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.services.DriverService;
import com.example.geektrust.services.RideService;
import com.example.geektrust.services.RiderService;
import com.example.geektrust.services.impl.DriverServiceImpl;
import com.example.geektrust.services.impl.RideServiceImpl;
import com.example.geektrust.services.impl.RiderServiceImpl;

public class RideSharingSystem {
  private final RideService rideService;
  private final DriverService driverService;
  private final RiderService riderService;

  public RideSharingSystem() {
    this.driverService = new DriverServiceImpl();
    this.riderService = new RiderServiceImpl();
    this.rideService = new RideServiceImpl(driverService, riderService);
  }

  public void processCommand(String command) {
    String[] tokens = command.split(" ");
    String commandType = tokens[0];

    switch (commandType) {
      case START_RIDE_COMMAND:
        handleStartRide(tokens);
        break;
      case STOP_RIDE_COMMAND:
        handleStopRide(tokens);
        break;
      case MATCH_COMMAND:
        handleMatch(tokens);
        break;
      case ADD_DRIVER_COMMAND:
        handleAddDriver(tokens);
        break;
      case ADD_RIDER_COMMAND:
        handleAddRider(tokens);
        break;
      case GET_BILL_COMMAND:
        handleGetBill(tokens);
        break;
      default:
        System.out.println("Invalid command: " + commandType);
        break;
    }
  }

  private void handleStartRide(String[] tokens) {
    String rideId = tokens[1];
    int nthDriver = Integer.parseInt(tokens[2]);
    String riderId = tokens[3];
    rideService.startRide(rideId, nthDriver, riderId);
  }

  private void handleStopRide(String[] tokens) {
    String rideId = tokens[1];
    long x = Long.parseLong(tokens[2]);
    long y = Long.parseLong(tokens[3]);
    long time = Long.parseLong(tokens[4]);
    Coordinates destination = new Coordinates(x, y);
    rideService.endRide(rideId, destination, time);
  }

  private void handleMatch(String[] tokens) {
    String riderId = tokens[1];
    rideService.printMatchedDrivers(riderId);
  }

  private void handleAddDriver(String[] tokens) {
    String driverId = tokens[1];
    long x = Long.parseLong(tokens[2]);
    long y = Long.parseLong(tokens[3]);
    driverService.addDriver(driverId, x, y);
  }

  private void handleAddRider(String[] tokens) {
    String riderId = tokens[1];
    long x = Long.parseLong(tokens[2]);
    long y = Long.parseLong(tokens[3]);
    riderService.addRider(riderId, x, y);
  }

  private void handleGetBill(String[] tokens) {
    String rideId = tokens[1];
    rideService.getRideBill(rideId);
  }
}
