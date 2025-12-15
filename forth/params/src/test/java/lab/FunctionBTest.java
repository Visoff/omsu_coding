package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionBTest {

    @Test
    void testConstructor() {
        FunctionB f = new FunctionB(2.0, Math.PI);
        assertNotNull(f);
    }

    @ParameterizedTest
    @CsvSource({
        "1.0, 1.0, 0.0, 0.0",
        "2.0, Math.PI, 0.5, 2.0",
        "1.0, 2.0, Math.PI/4, 1.0",  // sin(π/2) = 1
        "0.0, 5.0, 10.0, 0.0",
        "3.0, 0.0, 100.0, 0.0"
    })
    void testApply(double A, String bStr, String xStr, double expected) {
        double B = parseMathExpression(bStr);
        double x = parseMathExpression(xStr);
        FunctionB f = new FunctionB(A, B);
        double result = f.apply(x);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testBounds() {
        FunctionB f = new FunctionB(2.0, 3.0);
        assertEquals(Double.NEGATIVE_INFINITY, f.getLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, f.getUpperBound());
    }

    @Test
    void testPeriodicity() {
        FunctionB f = new FunctionB(1.0, 2.0);
        double period = Math.PI;
        
        double x1 = 0.0;
        double x2 = period;
        double x3 = 2 * period;
        
        assertEquals(f.apply(x1), f.apply(x2), 1e-10);
        assertEquals(f.apply(x1), f.apply(x3), 1e-10);
    }

    private double parseMathExpression(String expr) {
        switch (expr) {
            case "Math.PI": return Math.PI;
            case "Math.PI/2": return Math.PI / 2;
            case "Math.PI/4": return Math.PI / 4;
            default: return Double.parseDouble(expr);
        }
    }
}
