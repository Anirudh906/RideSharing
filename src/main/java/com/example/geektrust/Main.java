package com.example.geektrust;

import com.example.geektrust.controller.RideSharingSystem;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static RideSharingSystem rideSharingSystem = new RideSharingSystem();

  public static void main(String[] args) {
    // Sample code to read from a file passed as command line argument
    try {
      // the file to be opened for reading
      FileInputStream fis = new FileInputStream(args[0]);
      Scanner sc = new Scanner(fis); // file to be scanned
      // returns true if there is another line to read
      while (sc.hasNextLine()) {
        // Add your code here to process input commands
        rideSharingSystem.processCommand(sc.nextLine());
      }
      sc.close(); // closes the scanner
    } catch (IOException e) {
    }
  }
}
