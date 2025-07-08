package com.example.geektrust.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.geektrust.services.impl.BillServiceImpl;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class BillServiceImplTest {

  private final BillServiceImpl billService = new BillServiceImpl();

  @Test
  public void testGetRideBill_TypicalCase() {
    BigDecimal bill = billService.getRideBill(new BigDecimal("10.0"), 15L);
    assertNotNull(bill);
    assertTrue(bill.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  public void testGetRideBill_ZeroDistance() {
    BigDecimal bill = billService.getRideBill(BigDecimal.ZERO, 10L);
    assertNotNull(bill);
    assertTrue(bill.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  public void testGetRideBill_ZeroTime() {
    BigDecimal bill = billService.getRideBill(new BigDecimal("5.0"), 0L);
    assertNotNull(bill);
    assertTrue(bill.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  public void testGetRideBill_ZeroDistanceAndTime() {
    BigDecimal bill = billService.getRideBill(BigDecimal.ZERO, 0L);
    assertNotNull(bill);
    assertTrue(bill.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  public void testGetRideBill_Rounding() {
    BigDecimal bill = billService.getRideBill(new BigDecimal("3.333"), 7L);
    assertEquals(2, bill.scale());
  }
}
