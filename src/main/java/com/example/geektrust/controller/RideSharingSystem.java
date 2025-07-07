package com.example.geektrust.controller;

import static com.example.geektrust.constants.CommonConstants.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.services.BillService;
import com.example.geektrust.services.DriverService;
import com.example.geektrust.services.RideService;
import com.example.geektrust.services.RiderService;
import com.example.geektrust.services.impl.BillServiceImpl;
import com.example.geektrust.services.impl.DriverServiceImpl;
import com.example.geektrust.services.impl.RideServiceImpl;
import com.example.geektrust.services.impl.RiderServiceImpl;

public class RideSharingSystem {
  private RideService rideService;
  private DriverService driverService;
  private RiderService riderService;
  private BillService billService;

  public RideSharingSystem() {
    this.driverService = new DriverServiceImpl();
    this.riderService = new RiderServiceImpl();
    this.billService = new BillServiceImpl();
    this.rideService = new RideServiceImpl(driverService, riderService, billService);
  }

  public void processCommand(String command) {
    String[] commandParts = command.split(" ");
    switch (commandParts[0]) {
      case START_RIDE_COMMAND:
        rideService.startRide(commandParts[1], Integer.valueOf(commandParts[2]), commandParts[3]);
        break;
      case STOP_RIDE_COMMAND:
        rideService.endRide(
            commandParts[1],
            new Coordinates(Long.valueOf(commandParts[2]), Long.valueOf(commandParts[3])),
            Long.valueOf(commandParts[4]));
        break;
      case MATCH_COMMAND:
        rideService.printMatchedDrivers(commandParts[1]);
        break;
      case ADD_DRIVER_COMMAND:
        driverService.addDriver(
            commandParts[1], Long.valueOf(commandParts[2]), Long.valueOf(commandParts[3]));
        break;
      case ADD_RIDER_COMMAND:
        riderService.addRider(
            commandParts[1], Long.valueOf(commandParts[2]), Long.valueOf(commandParts[3]));
        break;
      case GET_BILL_COMMAND:
        rideService.getRideBill(commandParts[1]);
        break;
    }
  }
}
