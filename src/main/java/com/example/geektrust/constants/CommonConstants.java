package com.example.geektrust.constants;

import java.math.BigDecimal;

public class CommonConstants {
  public static final String NO_DRIVERS_AVAILABLE = "NO_DRIVERS_AVAILABLE";
  public static final Integer MAX_ALLOWED_DISTANCE = 5;
  public static final String INVALID_RIDE = "INVALID_RIDE";
  public static final String RIDE_STARTED = "RIDE_STARTED";
  public static final String RIDE_STOPPED = "RIDE_STOPPED";
  public static final String SPACE = " ";
  public static final String KEY_RIDE_ID = "rideId";
  public static final String KEY_RIDER_ID = "riderId";
  public static final String KEY_DRIVER_ID = "driverId";
  public static final String KEY_RIDE_BILL = "rideBill";
  public static final String KEY_RIDE_TIME = "rideTime";
  public static final String KEY_RIDE_DISTANCE = "rideDistanceInKm";
  public static final String KEY_RIDE_COMPLETED = "isRideCompleted";
  public static final Integer BASE_FARE = 50;
  public static final BigDecimal FARE_PER_KM = BigDecimal.valueOf(6.5);
  public static final BigDecimal FARE_PER_MINUTE = BigDecimal.valueOf(2);
  public static final BigDecimal SERVICE_TAX_MULTIPLIER = BigDecimal.valueOf(1.2);
  public static final Integer ROUND_SCALE_TWO = 2;
  public static final Integer ROUND_SCALE_ONE = 1;
  public static final String DRIVERS_MATCHED = "DRIVERS_MATCHED";
  public static final String ADD_DRIVER_COMMAND = "ADD_DRIVER";
  public static final String ADD_RIDER_COMMAND = "ADD_RIDER";
  public static final String MATCH_COMMAND = "MATCH";
  public static final String START_RIDE_COMMAND = "START_RIDE";
  public static final String STOP_RIDE_COMMAND = "STOP_RIDE";
  public static final String GET_BILL_COMMAND = "BILL";
  public static final Long INITIAL_RIDE_TIME = 0L;
  public static final Integer DRIVER_INDEX_OFFSET = 1;
  public static final int COMPARISON_EQUAL_OR_LESS = 0;
  public static final int SQUARE_EXPONENT = 2;
}
