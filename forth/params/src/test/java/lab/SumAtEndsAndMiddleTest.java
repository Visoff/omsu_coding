package lab;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class SumAtEndsAndMiddleTest {

    @Test
    void testApplyWithFunctionA() {
        FunctionA f = new FunctionA(2.0, 3.0);
        
        SingleArgumentFunctionFromRangeInterface boundedF = createBoundedFunction(f, 0.0, 4.0);
        
        SumAtEndsAndMiddle<SingleArgumentFunctionFromRangeInterface> summer = 
            new SumAtEndsAndMiddle<>();
        
        double result = summer.apply(boundedF);
        
        double expected = f.apply(0.0) + f.apply(2.0) + f.apply(4.0);
        assertEquals(expected, result, 1e-10);
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, 4.0, 1.0, 0.0, 6.0",
        "-2.0, 2.0, 0.0, 1.0, 3.0"
    })
    void testApplyVariousFunctions(double lower, double upper, double A, double B, double expected) {
        FunctionA f = new FunctionA(A, B);
        SingleArgumentFunctionFromRangeInterface boundedF = createBoundedFunction(f, lower, upper);
        
        SumAtEndsAndMiddle<SingleArgumentFunctionFromRangeInterface> summer = 
            new SumAtEndsAndMiddle<>();
        
        double result = summer.apply(boundedF);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testApplyWithSinusoidal() {
        FunctionB f = new FunctionB(1.0, 1.0);
        SingleArgumentFunctionFromRangeInterface boundedF = createBoundedFunction(f, 0.0, Math.PI);
        
        SumAtEndsAndMiddle<SingleArgumentFunctionFromRangeInterface> summer = 
            new SumAtEndsAndMiddle<>();
        
        double result = summer.apply(boundedF);
        double expected = Math.sin(0.0) + Math.sin(Math.PI/2) + Math.sin(Math.PI);
        assertEquals(expected, result, 1e-10);
    }

    @Test
    void testSymmetricFunction() {
        FunctionA oddFunction = new FunctionA(1.0, 0.0);
        SingleArgumentFunctionFromRangeInterface boundedF = createBoundedFunction(oddFunction, -2.0, 2.0);
        
        SumAtEndsAndMiddle<SingleArgumentFunctionFromRangeInterface> summer = 
            new SumAtEndsAndMiddle<>();
        
        double result = summer.apply(boundedF);
        assertEquals(0.0, result, 1e-10);
    }

    private SingleArgumentFunctionFromRangeInterface createBoundedFunction(
            final SingleArgumentFunctionFromRangeInterface f, 
            final double lower, 
            final double upper) {
        
        return new SingleArgumentFunctionFromRangeInterface() {
            @Override
            public double getLowerBound() {
                return lower;
            }
            
            @Override
            public double getUpperBound() {
                return upper;
            }
            
            @Override
            public double apply(double x) {
                return f.apply(x);
            }
        };
    }
}
