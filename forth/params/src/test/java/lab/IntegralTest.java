package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class IntegralTest {

    @Test
    void testConstructor() {
        Integral<FunctionA> integral = new Integral<>(0.0, 1.0);
        assertNotNull(integral);
    }

    @ParameterizedTest
    @CsvSource({
            "0.0, 1.0, 2.0, 3.0, 1.0",
            "0.0, 2.0, 1.0, 0.0, 0.0",
            "-1.0, 1.0, 0.0, 1.0, 0.5",
            "0.0, 3.1415, 1.0, 0.0, 0.0"
    })
    void testApplyWithFunctionA(double A, double B, double a, double b, double expected) {
        FunctionA f = new FunctionA(A, B);
        Integral<FunctionA> integral = new Integral<>(a, b);
        double result = integral.apply(f);
        assertEquals(expected, result, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 2.0, 3.0, 4.0, -1.0, 1.0, 1.0990912",
            "2.1, -2.0, 3.6, -0.2, 3.0, 3.1415926, 0.0580247",
            "1.3245, 2.3245, 3.1232, 4.9876, 2.7172, 3.1415, 0.1862267",
    })
    void testApplyWithFunctionC(double A, double B, double C, double D, double a, double b, double expected) {
        FunctionC f = new FunctionC(A, B, C, D);
        Integral<FunctionC> integral = new Integral<>(a, b);
        double result = integral.withPrecision(1e-7).apply(f);
        assertEquals(expected, result, 1e-7);
    }

    @Test
    void testApplyWithFunctionBounds() {
        SingleArgumentFunctionFromRangeInterface f = new SingleArgumentFunctionFromRangeInterface() {
            @Override
            public double getLowerBound() {
                return 0.0;
            }

            @Override
            public double getUpperBound() {
                return 10.0;
            }

            @Override
            public double apply(double x) {
                return 1.0;
            }
        };

        Integral<SingleArgumentFunctionFromRangeInterface> integral = new Integral<>(-5.0, 15.0);
        double result = integral.apply(f);

        assertEquals(10.0, result, 0.01);
    }

    @Test
    void testNumericalIntegrationPrecision() {
        FunctionA f = new FunctionA(0.0, 1.0);
        Integral<FunctionA> integral = new Integral<>(0.0, 10.0);
        double result = integral.apply(f);
        assertEquals(10.0, result, 0.01);
    }

    @Test
    void testEmptyIntegrationRange() {
        FunctionA f = new FunctionA(1.0, 0.0);
        SingleArgumentFunctionFromRangeInterface boundedF = new SingleArgumentFunctionFromRangeInterface() {
            @Override
            public double getLowerBound() {
                return 5.0;
            }

            @Override
            public double getUpperBound() {
                return 10.0;
            }

            @Override
            public double apply(double x) {
                return f.apply(x);
            }
        };

        Integral<SingleArgumentFunctionFromRangeInterface> integral = new Integral<>(0.0, 3.0);
        double result = integral.apply(boundedF);
        assertEquals(0.0, result, 1e-10);
    }
}
