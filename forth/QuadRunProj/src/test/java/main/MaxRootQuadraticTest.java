package main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

import quadratic.Quadratic;
import java.util.stream.Stream;

public class MaxRootQuadraticTest {

    private Quadratic quadratic;
    private MaxRootQuadratic maxRootQuadratic;

    @BeforeEach
    void setUp() {
        // Create fresh instances for each test
        quadratic = null;
        maxRootQuadratic = null;
    }

    @Test
    @DisplayName("Test constructor with valid Quadratic")
    void testConstructorWithValidQuadratic() {
        quadratic = new Quadratic(1, 2, 1);
        assertDoesNotThrow(() -> new MaxRootQuadratic(quadratic));
    }

    @Test
    @DisplayName("Test constructor with null Quadratic throws NullPointerException")
    void testConstructorWithNullQuadratic() {
        assertThrows(NullPointerException.class, () -> new MaxRootQuadratic(null));
    }

    @ParameterizedTest
    @MethodSource("twoRootsProvider")
    @DisplayName("Test getMaxRoot with two real roots")
    void testGetMaxRootWithTwoRoots(double a, double b, double c, double expectedMax)
            throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(a, b, c);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(expectedMax, result, 1e-10,
                String.format("For equation %.1fx² + %.1fx + %.1f = 0", a, b, c));
    }

    private static Stream<Arguments> twoRootsProvider() {
        return Stream.of(
                Arguments.of(1, -3, 2, 2.0), // x² - 3x + 2 = 0, roots: 1, 2
                Arguments.of(1, -5, 6, 3.0), // x² - 5x + 6 = 0, roots: 2, 3
                Arguments.of(1, 0, -4, 2.0), // x² - 4 = 0, roots: -2, 2
                Arguments.of(2, -5, -3, 3.0), // 2x² - 5x - 3 = 0, roots: -0.5, 3
                Arguments.of(1, 2, -3, 1.0), // x² + 2x - 3 = 0, roots: -3, 1
                Arguments.of(2, -3, 1, 1.0), // 2x² - 3x + 1 = 0, roots: 0.5, 1
                Arguments.of(1, -4, -5, 5.0) // x² - 4x - 5 = 0, roots: -1, 5
        );
    }

    @ParameterizedTest
    @MethodSource("singleRootProvider")
    @DisplayName("Test getMaxRoot with single root")
    void testGetMaxRootWithSingleRoot(double a, double b, double c, double expectedRoot)
            throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(a, b, c);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(expectedRoot, result, 1e-10,
                String.format("For equation %.1fx² + %.1fx + %.1f = 0", a, b, c));
    }

    private static Stream<Arguments> singleRootProvider() {
        return Stream.of(
                Arguments.of(1, -2, 1, 1.0), // x² - 2x + 1 = 0, root: 1 (double)
                Arguments.of(4, 4, 1, -0.5), // 4x² + 4x + 1 = 0, root: -0.5 (double)
                Arguments.of(0, 2, -4, 2.0), // 2x - 4 = 0, root: 2 (linear)
                Arguments.of(0, 3, 6, -2.0), // 3x + 6 = 0, root: -2 (linear)
                Arguments.of(1, 0, 0, 0.0), // x² = 0, root: 0
                Arguments.of(9, -6, 1, 1.0 / 3.0) // 9x² - 6x + 1 = 0, root: 1/3
        );
    }

    @ParameterizedTest
    @MethodSource("noRootsProvider")
    @DisplayName("Test getMaxRoot throws NoRootsException when no real roots")
    void testGetMaxRootThrowsNoRootsException(double a, double b, double c) {
        quadratic = new Quadratic(a, b, c);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        NoRootsException exception = assertThrows(NoRootsException.class,
                () -> maxRootQuadratic.getMaxRoot(),
                String.format("Should throw NoRootsException for %.1fx² + %.1fx + %.1f = 0", a, b, c));

        assertNotNull(exception.getMessage(), "Exception should have a message");
        assertFalse(exception.getMessage().isEmpty(), "Exception message should not be empty");
    }

    private static Stream<Arguments> noRootsProvider() {
        return Stream.of(
                Arguments.of(1, 0, 1), // x² + 1 = 0, no real roots
                Arguments.of(2, 2, 5), // 2x² + 2x + 5 = 0, no real roots
                Arguments.of(0, 0, 5), // 5 = 0, no solution
                Arguments.of(3, 2, 2), // 3x² + 2x + 2 = 0, no real roots
                Arguments.of(4, 0, 9) // 4x² + 9 = 0, no real roots
        );
    }

    @Test
    @DisplayName("Test getMaxRoot throws InfiniteRootsException for 0x² + 0x + 0 = 0")
    void testGetMaxRootThrowsInfiniteRootsException() {
        quadratic = new Quadratic(0, 0, 0);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        InfiniteRootsException exception = assertThrows(InfiniteRootsException.class,
                () -> maxRootQuadratic.getMaxRoot(),
                "Should throw InfiniteRootsException for 0=0");

        assertNotNull(exception.getMessage(), "Exception should have a message");
    }

    @Test
    @DisplayName("Test getMaxRoot with all roots negative")
    void testGetMaxRootWithNegativeRoots() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(1, 4, 3); // x² + 4x + 3 = 0, roots: -1, -3
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(-1.0, result, 1e-10, "Max root should be -1 (less negative)");
    }

    @Test
    @DisplayName("Test getMaxRoot with positive and negative roots")
    void testGetMaxRootWithMixedSignRoots() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(1, 1, -6); // x² + x - 6 = 0, roots: -3, 2
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(2.0, result, 1e-10, "Max root should be 2");
    }

    @Test
    @DisplayName("Test getMaxRoot with symmetric roots")
    void testGetMaxRootWithSymmetricRoots() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(1, 0, -9); // x² - 9 = 0, roots: -3, 3
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(3.0, result, 1e-10, "Max root should be 3");
    }

    @ParameterizedTest
    @CsvSource({
            "1e-10, 1e-10, 1e-10",
            "1e308, 1e308, 1e308",
            "1e-200, 1e-200, 1e-200"
    })
    @DisplayName("Test getMaxRoot with extreme coefficient values")
    void testGetMaxRootWithExtremeValues(double a, double b, double c) {
        quadratic = new Quadratic(a, b, c);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        // We don't know if these will have roots or not, but the method should handle
        // them
        assertDoesNotThrow(() -> {
            try {
                maxRootQuadratic.getMaxRoot();
                // If it doesn't throw, that's fine - we just want to make sure it doesn't crash
            } catch (NoRootsException | InfiniteRootsException e) {
                // These are expected exceptions for certain cases
            }
        }, "Should handle extreme values without crashing");
    }

    @Test
    @DisplayName("Test getMaxRoot returns larger root even when Quadratic returns roots in non-increasing order")
    void testGetMaxRootReturnsLargerRootRegardlessOfOrder() throws NoRootsException, InfiniteRootsException {
        // This equation should have roots 0.5 and 1.0
        // The Quadratic implementation might return them in any order
        quadratic = new Quadratic(2, -3, 1); // 2x² - 3x + 1 = 0, roots: 0.5, 1.0
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(1.0, result, 1e-10, "Should return max root 1.0 regardless of order");
    }

    @Test
    @DisplayName("Test getMaxRoot with multiple calls on same object returns consistent result")
    void testMultipleCallsOnSameObject() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(1, -3, 2); // Roots: 1, 2
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        // First call
        double firstResult = maxRootQuadratic.getMaxRoot();
        assertEquals(2.0, firstResult, 1e-10, "First call should return 2.0");

        // Second call should return same result
        double secondResult = maxRootQuadratic.getMaxRoot();
        assertEquals(2.0, secondResult, 1e-10, "Second call should return 2.0");
        assertEquals(firstResult, secondResult, 1e-10, "Multiple calls should return same value");
    }

    @Test
    @DisplayName("Test getMaxRoot with very close roots")
    void testGetMaxRootWithVeryCloseRoots() throws NoRootsException, InfiniteRootsException {
        // Equation with two very close roots
        quadratic = new Quadratic(1, -2.0000000001, 1.0000000001);
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        // This might have 0, 1, or 2 roots depending on floating point precision
        // Just ensure it doesn't crash
        assertDoesNotThrow(() -> maxRootQuadratic.getMaxRoot());
    }

    @Test
    @DisplayName("Test getMaxRoot with linear equation")
    void testGetMaxRootWithLinearEquation() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(0, 3, -9); // 3x - 9 = 0, root: 3
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        double result = maxRootQuadratic.getMaxRoot();
        assertEquals(3.0, result, 1e-10, "Should return root 3.0 for linear equation");
    }

    @Test
    @DisplayName("Test getMaxRoot with constant equation (no roots)")
    void testGetMaxRootWithConstantEquation() {
        quadratic = new Quadratic(0, 0, 7); // 7 = 0, no roots
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        assertThrows(NoRootsException.class,
                () -> maxRootQuadratic.getMaxRoot(),
                "Constant equation should throw NoRootsException");
    }

    @Test
    @DisplayName("Test exception messages contain useful information")
    void testExceptionMessages() {
        // Test NoRootsException message
        quadratic = new Quadratic(1, 0, 1); // x² + 1 = 0, no real roots
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        NoRootsException noRootsException = assertThrows(NoRootsException.class,
                () -> maxRootQuadratic.getMaxRoot());

        assertNotNull(noRootsException.getMessage(),
                "NoRootsException should have a message");

        // Test InfiniteRootsException message
        quadratic = new Quadratic(0, 0, 0); // 0 = 0, infinite roots
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        InfiniteRootsException infiniteRootsException = assertThrows(InfiniteRootsException.class,
                () -> maxRootQuadratic.getMaxRoot());

        assertNotNull(infiniteRootsException.getMessage(),
                "InfiniteRootsException should have a message");
    }

    @Test
    @DisplayName("Test that MaxRootQuadratic doesn't modify the underlying Quadratic")
    void testDoesNotModifyQuadratic() throws NoRootsException, InfiniteRootsException {
        quadratic = new Quadratic(1, -5, 6); // x² - 5x + 6 = 0, roots: 2, 3
        maxRootQuadratic = new MaxRootQuadratic(quadratic);

        // Get max root
        double firstMax = maxRootQuadratic.getMaxRoot();
        assertEquals(3.0, firstMax, 1e-10);

        // Create another MaxRootQuadratic with the same Quadratic
        MaxRootQuadratic anotherMaxRootQuadratic = new MaxRootQuadratic(quadratic);
        double secondMax = anotherMaxRootQuadratic.getMaxRoot();

        assertEquals(firstMax, secondMax, 1e-10,
                "Both instances should return the same max root from the same Quadratic");
    }
}
