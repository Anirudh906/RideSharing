package com.example.geektrust.exceptions;

public class DriverDoesntExistException extends RuntimeException {
  public DriverDoesntExistException(String message) {
    super(message);
  }
}
