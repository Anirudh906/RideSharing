package com.example.geektrust;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  void testMainWithValidInputFile() throws IOException {
    // Create a temporary file with sample commands
    File tempFile = File.createTempFile("input", ".txt");
    FileWriter writer = new FileWriter(tempFile);
    writer.write("SAMPLE_COMMAND\n");
    writer.close();

    String[] args = {tempFile.getAbsolutePath()};

    // Should not throw any exception
    assertDoesNotThrow(() -> Main.main(args));

    tempFile.delete();
  }

  @Test
  void testMainWithMissingFile() {
    String[] args = {"non_existent_file.txt"};
    // Should not throw any exception (IOException is caught)
    assertDoesNotThrow(() -> Main.main(args));
  }
}
