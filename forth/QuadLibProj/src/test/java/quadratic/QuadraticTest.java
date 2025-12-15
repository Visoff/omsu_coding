package quadratic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class QuadraticTest {
    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new Quadratic(1.0, 2.0, 1.0));
    }

    @ParameterizedTest
    @MethodSource("infiniteRootsData")
    void testAreInfiniteRoots(double a, double b, double c, boolean expected) {
        Quadratic q = new Quadratic(a, b, c);
        assertEquals(expected, q.areInfiniteRoots());
    }

    @ParameterizedTest
    @MethodSource("rootsData")
    void testGetRoots(double a, double b, double c, double[] expectedRoots) {
        Quadratic q = new Quadratic(a, b, c);
        double[] roots = q.getRoots();
        
        if (expectedRoots.length == 0) {
            assertEquals(0, roots.length);
        } else if (expectedRoots.length == 1) {
            assertEquals(1, roots.length);
            assertEquals(expectedRoots[0], roots[0], 1e-10);
        } else {
            assertEquals(2, roots.length);
            boolean match1 = Math.abs(expectedRoots[0] - roots[0]) < 1e-10 && 
                            Math.abs(expectedRoots[1] - roots[1]) < 1e-10;
            boolean match2 = Math.abs(expectedRoots[0] - roots[1]) < 1e-10 && 
                            Math.abs(expectedRoots[1] - roots[0]) < 1e-10;
            assertTrue(match1 || match2);
        }
    }

    @Test
    void testLinearEquation() {
        Quadratic q = new Quadratic(0, 2, 4);
        double[] roots = q.getRoots();
        assertEquals(1, roots.length);
        assertEquals(-2.0, roots[0], 1e-10);
    }

    @Test
    void testNoSolutionLinear() {
        Quadratic q = new Quadratic(0, 0, 5);
        double[] roots = q.getRoots();
        assertEquals(0, roots.length);
    }

    @ParameterizedTest
    @MethodSource("discriminantData")
    void testDiscriminantCases(double a, double b, double c, int expectedRootCount) {
        Quadratic q = new Quadratic(a, b, c);
        double[] roots = q.getRoots();
        assertEquals(expectedRootCount, roots.length);
    }

    private static Stream<Arguments> infiniteRootsData() {
        return Stream.of(
            Arguments.of(0, 0, 0, true),
            Arguments.of(1, 2, 3, false),
            Arguments.of(0, 0, 5, false),
            Arguments.of(0, 3, 0, false),
            Arguments.of(1e-10, 1e-10, 1e-10, false)
        );
    }

    private static Stream<Arguments> rootsData() {
        return Stream.of(
            Arguments.of(1, -3, 2, new double[]{1.0, 2.0}),
            Arguments.of(1, 0, -1, new double[]{-1.0, 1.0}),
            Arguments.of(1, 2, 1, new double[]{-1.0}),
            Arguments.of(4, 4, 1, new double[]{-0.5}),
            Arguments.of(1, 0, 1, new double[]{}),
            Arguments.of(0, 2, -4, new double[]{2.0}),
            Arguments.of(0, 1, 0, new double[]{0.0}),
            Arguments.of(0, 0, 5, new double[]{})
        );
    }

    private static Stream<Arguments> discriminantData() {
        return Stream.of(
            Arguments.of(1, -5, 6, 2),
            Arguments.of(1, -4, 4, 1),
            Arguments.of(1, 2, 5, 0),
            Arguments.of(2, -11, 5, 2),
            Arguments.of(9, -6, 1, 1)
        );
    }

    @Test
    void testExtremeValues() {
        Quadratic q1 = new Quadratic(1e-308, 1e-308, 1e-308);
        double[] roots1 = q1.getRoots();
        assertNotNull(roots1);

        Quadratic q2 = new Quadratic(1e308, 1e308, 1e308);
        double[] roots2 = q2.getRoots();
        assertNotNull(roots2);
    }

    @Test
    void testRootsOrdering() {
        Quadratic q = new Quadratic(2, -5, 2);
        double[] roots = q.getRoots();
        assertEquals(2, roots.length);
        double expectedRoot1 = 0.5;
        double expectedRoot2 = 2.0;
        
        boolean hasRoot1 = Math.abs(roots[0] - expectedRoot1) < 1e-10 || 
                          Math.abs(roots[1] - expectedRoot1) < 1e-10;
        boolean hasRoot2 = Math.abs(roots[0] - expectedRoot2) < 1e-10 || 
                          Math.abs(roots[1] - expectedRoot2) < 1e-10;
        
        assertTrue(hasRoot1 && hasRoot2);
    }
}
