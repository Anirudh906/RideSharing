package com.example.geektrust.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.domain.internal.Driver;
import com.example.geektrust.exceptions.DriverDoesntExistException;
import com.example.geektrust.services.impl.DriverServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DriverServiceImplTest {

  private DriverServiceImpl driverService;

  @BeforeEach
  public void setUp() {
    driverService = new DriverServiceImpl();
  }

  @Test
  public void testAddDriverAndGetCoordinates() {
    driverService.addDriver("D1", 10L, 20L);
    Coordinates coords = driverService.getDriverCoordinates("D1");
    assertEquals(10L, coords.getXCoordinate());
    assertEquals(20L, coords.getYCoordinate());
  }

  @Test
  public void testGetDriverCoordinates_DriverDoesNotExist() {
    assertThrows(DriverDoesntExistException.class, () -> driverService.getDriverCoordinates("D2"));
  }

  @Test
  public void testUpdateDriverAvailability() {
    driverService.addDriver("D1", 0L, 0L);
    driverService.updateDriverAvailability("D1", false);
    Map<String, Driver> availableDrivers = driverService.getAllDrivers();
    assertFalse(availableDrivers.containsKey("D1"));
    driverService.updateDriverAvailability("D1", true);
    availableDrivers = driverService.getAllDrivers();
    assertTrue(availableDrivers.containsKey("D1"));
  }

  @Test
  public void testUpdateDriverAvailability_DriverDoesNotExist() {
    assertThrows(
        DriverDoesntExistException.class, () -> driverService.updateDriverAvailability("D3", true));
  }

  @Test
  public void testUpdateDriverCoordinates() {
    driverService.addDriver("D1", 1L, 2L);
    Coordinates newCoords = new Coordinates(5L, 6L);
    driverService.updateDriverCoordinates("D1", newCoords);
    Coordinates coords = driverService.getDriverCoordinates("D1");
    assertEquals(5L, coords.getXCoordinate());
    assertEquals(6L, coords.getYCoordinate());
  }

  @Test
  public void testUpdateDriverCoordinates_DriverDoesNotExist() {
    Coordinates coords = new Coordinates(1L, 1L);
    assertThrows(
        DriverDoesntExistException.class,
        () -> driverService.updateDriverCoordinates("D4", coords));
  }

  @Test
  public void testGetAllDrivers_OnlyAvailable() {
    driverService.addDriver("D1", 0L, 0L);
    driverService.addDriver("D2", 1L, 1L);
    driverService.updateDriverAvailability("D2", false);
    Map<String, Driver> availableDrivers = driverService.getAllDrivers();
    assertTrue(availableDrivers.containsKey("D1"));
    assertFalse(availableDrivers.containsKey("D2"));
  }
}
