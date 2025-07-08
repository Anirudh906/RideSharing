package com.example.geektrust.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.domain.internal.*;
import com.example.geektrust.services.impl.RideServiceImpl;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RideServiceImplTest {

  private RideServiceImpl rideService;
  private DriverService driverService;
  private RiderService riderService;
  private BillService billService;

  @BeforeEach
  void setUp() {
    driverService =
        new DriverService() {
          private final Map<String, Driver> drivers = new HashMap<>();

          @Override
          public void addDriver(String id, Long x, Long y) {
            drivers.put(id, new Driver(id, x, y));
          }

          @Override
          public Map<String, Driver> getAllDrivers() {
            return new HashMap<>(drivers);
          }

          @Override
          public Coordinates getDriverCoordinates(String id) {
            return drivers.get(id).getCoordinates();
          }

          @Override
          public void updateDriverAvailability(String driverId, Boolean availability) {
            drivers.get(driverId).updateAvailability(availability);
          }

          @Override
          public void updateDriverCoordinates(String id, Coordinates c) {
            drivers.get(id).updateCoordinates(c.getXCoordinate(), c.getYCoordinate());
          }
        };
    riderService =
        new RiderService() {
          private final Map<String, Coordinates> riders = new HashMap<>();

          @Override
          public void addRider(String id, Long x, Long y) {
            riders.put(id, new Coordinates(x, y));
          }

          @Override
          public Coordinates getRiderCoordinates(String id) {
            return riders.get(id);
          }

          @Override
          public void updateRiderCoordinates(String id, Coordinates c) {
            riders.put(id, c);
          }
        };
    billService = (distance, time) -> BigDecimal.valueOf(100);
    rideService = new RideServiceImpl(driverService, riderService, billService);
  }

  @Test
  void testStartRide_Valid() {
    driverService.addDriver("D1", 0L, 0L);
    riderService.addRider("R1", 0L, 1L);
    rideService.startRide("RIDE1", 1, "R1");
    // No exception, ride should be started
  }

  @Test
  void testStartRide_InvalidNthDriver() {
    driverService.addDriver("D1", 0L, 0L);
    riderService.addRider("R1", 0L, 1L);
    rideService.startRide("RIDE1", 2, "R1");
    // Should print INVALID_RIDE, but no exception
  }

  @Test
  void testEndRide_Valid() {
    driverService.addDriver("D1", 0L, 0L);
    riderService.addRider("R1", 0L, 1L);
    rideService.startRide("RIDE1", 1, "R1");
    rideService.endRide("RIDE1", new Coordinates(1L, 1L), 10L);
    // Should complete ride
  }

  @Test
  void testEndRide_InvalidRide() {
    rideService.endRide("NON_EXISTENT", new Coordinates(1L, 1L), 10L);
    // Should print INVALID_RIDE
  }

  @Test
  void testGetDriverWithinRange() {
    driverService.addDriver("D1", 0L, 0L);
    driverService.addDriver("D2", 10L, 10L);
    riderService.addRider("R1", 0L, 1L);
    Map<String, Driver> drivers = rideService.getDriverWithinRange("R1");
    assertTrue(drivers.containsKey("D1"));
  }

  @Test
  void testPrintMatchedDrivers_NoDrivers() {
    riderService.addRider("R1", 0L, 1L);
    rideService.printMatchedDrivers("R1");
    // Should print NO_DRIVERS_AVAILABLE
  }

  @Test
  void testGetRideBill_Valid() {
    driverService.addDriver("D1", 0L, 0L);
    riderService.addRider("R1", 0L, 1L);
    rideService.startRide("RIDE1", 1, "R1");
    rideService.endRide("RIDE1", new Coordinates(1L, 1L), 10L);
    rideService.getRideBill("RIDE1");
    // Should print bill
  }

  @Test
  void testGetRideBill_InvalidRide() {
    rideService.getRideBill("NON_EXISTENT");
    // Should print INVALID_RIDE
  }
}
