package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class UpTriangleMatrixTest {
    private UpTriangleMatrix upperMatrix;

    @BeforeEach
    void setUp() {
        upperMatrix = new UpTriangleMatrix(3);
        // [1, 2, 3]
        // [0, 4, 5]
        // [0, 0, 6]
        upperMatrix.set(0, 0, 1);
        upperMatrix.set(0, 1, 2);
        upperMatrix.set(0, 2, 3);
        upperMatrix.set(1, 1, 4);
        upperMatrix.set(1, 2, 5);
        upperMatrix.set(2, 2, 6);
    }

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new UpTriangleMatrix(-1));
        assertThrows(IllegalArgumentException.class, () -> new UpTriangleMatrix(0));

        UpTriangleMatrix m = new UpTriangleMatrix(5);
        assertEquals(5, m.getSize());
    }

    @Test
    void testGetSet() {
        assertEquals(1, upperMatrix.get(0, 0), 1e-10);
        assertEquals(2, upperMatrix.get(0, 1), 1e-10);
        assertEquals(3, upperMatrix.get(0, 2), 1e-10);
        assertEquals(0, upperMatrix.get(1, 0), 1e-10);
        assertEquals(4, upperMatrix.get(1, 1), 1e-10);
        assertEquals(5, upperMatrix.get(1, 2), 1e-10);
        assertEquals(0, upperMatrix.get(2, 0), 1e-10);
        assertEquals(0, upperMatrix.get(2, 1), 1e-10);
        assertEquals(6, upperMatrix.get(2, 2), 1e-10);

        upperMatrix.set(1, 2, 10);
        assertEquals(10, upperMatrix.get(1, 2), 1e-10);
    }

    @Test
    void testSetNonZeroInLowerTriangle() {
        assertThrows(IllegalArgumentException.class, () -> upperMatrix.set(1, 0, 5));
        assertThrows(IllegalArgumentException.class, () -> upperMatrix.set(2, 0, 0.1));
        assertThrows(IllegalArgumentException.class, () -> upperMatrix.set(2, 1, 2));

        assertDoesNotThrow(() -> upperMatrix.set(1, 0, 0));
        assertDoesNotThrow(() -> upperMatrix.set(2, 1, 0));
    }

    @Test
    void testDet() {
        assertEquals(24, upperMatrix.det(), 1e-10); // 1 * 4 * 6 = 24

        upperMatrix.set(2, 2, 0);
        assertEquals(0, upperMatrix.det(), 1e-10); // 1 * 4 * 0 = 0
    }

    @Test
    void testDetCache() {
        UpTriangleMatrix m = new UpTriangleMatrix(2);
        m.set(0, 0, 2);
        m.set(0, 1, 3);
        m.set(1, 1, 4);

        double det1 = m.det();
        double det2 = m.det();
        assertEquals(det1, det2, 1e-10);

        m.set(1, 1, 5);
        double det3 = m.det();
        assertNotEquals(det1, det3, 1e-10);
    }

    @Test
    void testEquals() {
        UpTriangleMatrix m1 = new UpTriangleMatrix(2);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(1, 1, 3);

        UpTriangleMatrix m2 = new UpTriangleMatrix(2);
        m2.set(0, 0, 1);
        m2.set(0, 1, 2);
        m2.set(1, 1, 3);

        UpTriangleMatrix m3 = new UpTriangleMatrix(2);
        m3.set(0, 0, 1);
        m3.set(0, 1, 2);
        m3.set(1, 1, 4);

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertNotEquals(m1, null);
        assertNotEquals(m1, "не матрица");
    }

    @Test
    void testHashCode() {
        UpTriangleMatrix m1 = new UpTriangleMatrix(2);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(1, 1, 3);

        UpTriangleMatrix m2 = new UpTriangleMatrix(2);
        m2.set(0, 0, 1);
        m2.set(0, 1, 2);
        m2.set(1, 1, 3);

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testGetIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> upperMatrix.get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> upperMatrix.get(3, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> upperMatrix.get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> upperMatrix.get(0, 3));
    }
}
