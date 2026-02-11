package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DiagMatrixTest {
    private DiagMatrix diagMatrix;

    @BeforeEach
    void setUp() {
        diagMatrix = new DiagMatrix(3);
        diagMatrix.set(0, 0, 1);
        diagMatrix.set(1, 1, 2);
        diagMatrix.set(2, 2, 3);
    }

    @Test
    void testConstructors() {
        assertThrows(IllegalArgumentException.class, () -> new DiagMatrix(-1));
        assertThrows(IllegalArgumentException.class, () -> new DiagMatrix(0));

        DiagMatrix m1 = new DiagMatrix(5);
        assertEquals(5, m1.getSize());

        DiagMatrix m2 = new DiagMatrix(1, 2, 3, 4);
        assertEquals(4, m2.getSize());
        assertEquals(1, m2.get(0, 0), 1e-10);
        assertEquals(2, m2.get(1, 1), 1e-10);
        assertEquals(3, m2.get(2, 2), 1e-10);
        assertEquals(4, m2.get(3, 3), 1e-10);
    }

    @Test
    void testGetSet() {
        assertEquals(1, diagMatrix.get(0, 0), 1e-10);
        assertEquals(0, diagMatrix.get(0, 1), 1e-10);
        assertEquals(2, diagMatrix.get(1, 1), 1e-10);
        assertEquals(0, diagMatrix.get(1, 0), 1e-10);

        diagMatrix.set(1, 1, 10);
        assertEquals(10, diagMatrix.get(1, 1), 1e-10);
    }

    @Test
    void testSetNonZeroOffDiagonal() {
        assertThrows(IllegalArgumentException.class, () -> diagMatrix.set(0, 1, 5));
        assertThrows(IllegalArgumentException.class, () -> diagMatrix.set(1, 0, 5));
        assertThrows(IllegalArgumentException.class, () -> diagMatrix.set(1, 2, 0.1));

        assertDoesNotThrow(() -> diagMatrix.set(0, 1, 0));
        assertDoesNotThrow(() -> diagMatrix.set(1, 0, 0));
    }

    @Test
    void testDet() {
        assertEquals(6, diagMatrix.det(), 1e-10); // 1 * 2 * 3 = 6

        diagMatrix.set(2, 2, 0);
        assertEquals(0, diagMatrix.det(), 1e-10); // 1 * 2 * 0 = 0
    }

    @Test
    void testDetCache() {
        DiagMatrix m = new DiagMatrix(2, 3);
        double det1 = m.det();
        double det2 = m.det();
        assertEquals(det1, det2, 1e-10);

        m.set(1, 1, 4);
        double det3 = m.det();
        assertNotEquals(det1, det3, 1e-10);
    }

    @Test
    void testEquals() {
        DiagMatrix m1 = new DiagMatrix(1, 2, 3);
        DiagMatrix m2 = new DiagMatrix(1, 2, 3);
        DiagMatrix m3 = new DiagMatrix(1, 2, 4);
        DiagMatrix m4 = new DiagMatrix(4);
        m4.set(0, 0, 1);
        m4.set(1, 1, 2);
        m4.set(2, 2, 3);

        assertEquals(m1, m2);
        assertNotEquals(m1, m4);
        assertNotEquals(m1, m3);
        assertNotEquals(m1, null);
        assertNotEquals(m1, "не матрица");
    }

    @Test
    void testHashCode() {
        DiagMatrix m1 = new DiagMatrix(1, 2, 3);
        DiagMatrix m2 = new DiagMatrix(1, 2, 3);

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testGetIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> diagMatrix.get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> diagMatrix.get(3, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> diagMatrix.get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> diagMatrix.get(0, 3));
    }
}
