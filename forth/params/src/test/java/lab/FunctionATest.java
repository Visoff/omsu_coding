package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionATest {

    @Test
    void testConstructor() {
        FunctionA f = new FunctionA(2.0, 3.0);
        assertNotNull(f);
    }

    @ParameterizedTest
    @CsvSource({
        "2.0, 3.0, 0.0, 3.0",
        "2.0, 3.0, 1.0, 5.0",
        "2.0, 3.0, -1.0, 1.0",
        "0.0, 5.0, 10.0, 5.0",
        "-2.0, 3.0, 2.0, -1.0"
    })
    void testApply(double A, double B, double x, double expected) {
        FunctionA f = new FunctionA(A, B);
        double result = f.apply(x);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testBounds() {
        FunctionA f = new FunctionA(2.0, 3.0);
        assertEquals(Double.NEGATIVE_INFINITY, f.getLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, f.getUpperBound());
    }

    @Test
    void testEdgeCases() {
        FunctionA f1 = new FunctionA(Double.MAX_VALUE, Double.MAX_VALUE);
        double result1 = f1.apply(1.0);
        assertTrue(Double.isFinite(result1) || Double.isInfinite(result1));

        FunctionA f2 = new FunctionA(Double.MIN_VALUE, Double.MIN_VALUE);
        double result2 = f2.apply(1.0);
        assertTrue(Double.isFinite(result2));
    }
}
