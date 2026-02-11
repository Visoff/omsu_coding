package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    void setUp() {
        matrix = new Matrix(3);
        // [1, 2, 3]
        // [4, 5, 6]
        // [7, 8, 9]
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix.set(i, j, i * 3 + j + 1);
            }
        }
    }

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix(-1));
        assertThrows(IllegalArgumentException.class, () -> new Matrix(0));

        Matrix m = new Matrix(5);
        assertEquals(5, m.getSize());
    }

    @Test
    void testGetSet() {
        assertEquals(1, matrix.get(0, 0), 1e-10);
        assertEquals(5, matrix.get(1, 1), 1e-10);
        assertEquals(9, matrix.get(2, 2), 1e-10);

        matrix.set(1, 1, 10);
        assertEquals(10, matrix.get(1, 1), 1e-10);
    }

    @Test
    void testGetIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(3, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.get(0, 3));
    }

    @Test
    void testSetIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(-1, 0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(3, 0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(0, -1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(0, 3, 1));
    }

    @Test
    void testDet() {
        assertEquals(0, matrix.det(), 1e-10);

        // [1, 0, 0]
        // [0, 1, 0]
        // [0, 0, 1]
        Matrix identity = new Matrix(3);
        identity.set(0, 0, 1);
        identity.set(1, 1, 1);
        identity.set(2, 2, 1);
        assertEquals(1, identity.det(), 1e-10);

        // [1, 2]
        // [3, 4]
        Matrix m2x2 = new Matrix(2);
        m2x2.set(0, 0, 1);
        m2x2.set(0, 1, 2);
        m2x2.set(1, 0, 3);
        m2x2.set(1, 1, 4);
        assertEquals(-2, m2x2.det(), 1e-10);
    }

    @Test
    void testDetCache() {
        // [1, 2]
        // [3, 4]
        Matrix m = new Matrix(2);
        m.set(0, 0, 1);
        m.set(0, 1, 2);
        m.set(1, 0, 3);
        m.set(1, 1, 4);

        double det1 = m.det();
        double det2 = m.det();
        assertEquals(det1, det2, 1e-10);

        // [1, 2]
        // [3, 5]
        m.set(1, 1, 5);
        double det3 = m.det();
        assertNotEquals(det1, det3, 1e-10);
    }

    @Test
    void testEquals() {
        Matrix m1 = new Matrix(2);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(1, 0, 3);
        m1.set(1, 1, 4);

        Matrix m2 = new Matrix(2);
        m2.set(0, 0, 1);
        m2.set(0, 1, 2);
        m2.set(1, 0, 3);
        m2.set(1, 1, 4);

        Matrix m3 = new Matrix(2);
        m3.set(0, 0, 1);
        m3.set(0, 1, 2);
        m3.set(1, 0, 3);
        m3.set(1, 1, 5);

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertNotEquals(m1, null);
        assertNotEquals(m1, "не матрица");
    }

    @Test
    void testHashCode() {
        Matrix m1 = new Matrix(2);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(1, 0, 3);
        m1.set(1, 1, 4);

        Matrix m2 = new Matrix(2);
        m2.set(0, 0, 1);
        m2.set(0, 1, 2);
        m2.set(1, 0, 3);
        m2.set(1, 1, 4);

        assertEquals(m1.hashCode(), m2.hashCode());
    }
}
