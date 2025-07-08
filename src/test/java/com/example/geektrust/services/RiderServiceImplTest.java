package com.example.geektrust.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.domain.internal.Coordinates;
import com.example.geektrust.exceptions.RiderDoesntExistException;
import com.example.geektrust.services.impl.RiderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RiderServiceImplTest {

  private RiderServiceImpl riderService;

  @BeforeEach
  public void setUp() {
    riderService = new RiderServiceImpl();
  }

  @Test
  public void testAddRiderAndGetCoordinates() {
    riderService.addRider("R1", 5L, 10L);
    Coordinates coords = riderService.getRiderCoordinates("R1");
    assertEquals(5L, coords.getXCoordinate());
    assertEquals(10L, coords.getYCoordinate());
  }

  @Test
  public void testGetRiderCoordinates_RiderDoesNotExist() {
    assertThrows(RiderDoesntExistException.class, () -> riderService.getRiderCoordinates("R2"));
  }

  @Test
  public void testUpdateRiderCoordinates() {
    riderService.addRider("R1", 1L, 2L);
    Coordinates newCoords = new Coordinates(7L, 8L);
    riderService.updateRiderCoordinates("R1", newCoords);
    Coordinates coords = riderService.getRiderCoordinates("R1");
    assertEquals(7L, coords.getXCoordinate());
    assertEquals(8L, coords.getYCoordinate());
  }

  @Test
  public void testUpdateRiderCoordinates_RiderDoesNotExist() {
    Coordinates coords = new Coordinates(3L, 4L);
    assertThrows(
        RiderDoesntExistException.class, () -> riderService.updateRiderCoordinates("R3", coords));
  }
}
