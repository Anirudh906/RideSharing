package com.example.geektrust.exceptions;

public class RiderDoesntExistException extends RuntimeException {
  public RiderDoesntExistException(String message) {
    super(message);
  }
}
