package main;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    void testAppMainOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            App.main(new String[]{});
            
            String output = outputStream.toString().trim();
            String[] lines = output.split(System.lineSeparator());
            
            assertEquals(2, lines.length);
            
            double root1 = Double.parseDouble(lines[0]);
            double root2 = Double.parseDouble(lines[1]);
            
            assertTrue((Math.abs(root1 - 1.0) < 1e-10 && Math.abs(root2 + 1.0) < 1e-10) ||
                      (Math.abs(root1 + 1.0) < 1e-10 && Math.abs(root2 - 1.0) < 1e-10));
            
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testAppClassExists() {
        App app = null;
        try {
            new App();
        } catch (Exception e) {
            fail("Could not instantiate App class: " + e.getMessage());
        }
    }
}
