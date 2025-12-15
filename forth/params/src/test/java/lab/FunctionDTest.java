package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionDTest {

    @Test
    void testConstructor() {
        FunctionD f = new FunctionD(2.0, 3.0);
        assertNotNull(f);
    }

    @ParameterizedTest
    @CsvSource({
        "1.0, 0.0, 0.0, 1.0",
        "2.0, 3.0, 0.0, 5.0",
        "1.0, 1.0, 1.0, 3.718281828459045",
        "0.0, 5.0, 10.0, 5.0",
        "-1.0, 2.0, 1.0, -0.718281828459045"
    })
    void testApply(double A, double B, double x, double expected) {
        FunctionD f = new FunctionD(A, B);
        double result = f.apply(x);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testBounds() {
        FunctionD f = new FunctionD(2.0, 3.0);
        assertEquals(Double.NEGATIVE_INFINITY, f.getLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, f.getUpperBound());
    }

    @Test
    void testExponentialProperties() {
        FunctionD f = new FunctionD(1.0, 0.0);
        
        assertEquals(1.0, f.apply(0.0), 1e-10);
        
        assertEquals(Math.E, f.apply(1.0), 1e-10);
        
        double a = 1.5;
        double b = 2.5;
        double product = f.apply(a) * f.apply(b);
        double sum = f.apply(a + b);
        assertEquals(product, sum, 1e-10);
    }

    @Test
    void testLargeValues() {
        FunctionD f = new FunctionD(1.0, 0.0);
        double largeResult = f.apply(100.0);
        assertTrue(Double.isFinite(largeResult) || Double.isInfinite(largeResult));
        
        double smallResult = f.apply(-100.0);
        assertTrue(smallResult > 0.0 && smallResult < 1e-10);
    }
}
