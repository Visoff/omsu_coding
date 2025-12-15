package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionCTest {

    @Test
    void testConstructor() {
        FunctionC f = new FunctionC(1.0, 2.0, 3.0, 4.0);
        assertNotNull(f);
    }

    @ParameterizedTest
    @CsvSource({
        "1.0, 2.0, 3.0, 4.0, 0.0, 0.5",
        "1.0, 0.0, 1.0, 0.0, 2.0, 1.0",
        "0.0, 5.0, 1.0, 2.0, 3.0, 1.0",
        "2.0, 4.0, 1.0, 0.0, 1.0, 6.0"
    })
    void testApply(double A, double B, double C, double D, double x, double expected) {
        FunctionC f = new FunctionC(A, B, C, D);
        double result = f.apply(x);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testBounds() {
        FunctionC f = new FunctionC(1.0, 2.0, 3.0, 4.0);
        assertEquals(Double.NEGATIVE_INFINITY, f.getLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, f.getUpperBound());
    }

    @Test
    void testVerticalAsymptote() {
        FunctionC f = new FunctionC(1.0, 1.0, 1.0, -2.0);
        
        double x1 = 1.9999;
        double x2 = 2.0001;
        
        assertDoesNotThrow(() -> f.apply(x1));
        assertDoesNotThrow(() -> f.apply(x2));
    }

    @Test
    void testLinearCase() {
        FunctionC f = new FunctionC(2.0, 4.0, 0.0, 1.0);
        assertEquals(6.0, f.apply(1.0), 1e-10);
        assertEquals(0.0, f.apply(-2.0), 1e-10);
    }
}
