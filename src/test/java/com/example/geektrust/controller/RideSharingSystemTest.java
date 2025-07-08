package com.example.geektrust.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RideSharingSystemTest {

  private RideSharingSystem system;

  @BeforeEach
  public void setUp() {
    system = new RideSharingSystem();
  }

  @Test
  public void testAddDriver() {
    system.processCommand("ADD_DRIVER D1 0 0");
    // No exception means success; output is printed to console
  }

  @Test
  public void testAddRider() {
    system.processCommand("ADD_RIDER R1 1 1");
  }

  @Test
  public void testMatchDrivers() {
    system.processCommand("ADD_DRIVER D1 0 0");
    system.processCommand("ADD_RIDER R1 1 1");
    system.processCommand("MATCH R1");
  }

  @Test
  public void testStartAndStopRide() {
    system.processCommand("ADD_DRIVER D1 0 0");
    system.processCommand("ADD_RIDER R1 1 1");
    system.processCommand("START_RIDE RIDE1 1 R1");
    system.processCommand("STOP_RIDE RIDE1 2 2 10");
  }

  @Test
  public void testGetBill() {
    system.processCommand("ADD_DRIVER D1 0 0");
    system.processCommand("ADD_RIDER R1 1 1");
    system.processCommand("START_RIDE RIDE1 1 R1");
    system.processCommand("STOP_RIDE RIDE1 2 2 10");
    system.processCommand("BILL RIDE1");
  }
}
