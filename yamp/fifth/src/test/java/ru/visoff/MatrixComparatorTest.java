package ru.visoff;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MatrixComparatorTest {
    @Test
    void testCompare() {
        MatrixComparator comparator = new MatrixComparator();

        DiagMatrix m1 = new DiagMatrix(1, 2, 3);

        UpTriangleMatrix m2 = new UpTriangleMatrix(3);
        m2.set(0, 0, 1);
        m2.set(0, 1, 2);
        m2.set(0, 2, 3);
        m2.set(1, 1, 4);
        m2.set(1, 2, 5);
        m2.set(2, 2, 6);

        Matrix m3 = new Matrix(2);
        m3.set(0, 0, 1);
        m3.set(0, 1, 2);
        m3.set(1, 0, 3);
        m3.set(1, 1, 4);

        assertTrue(comparator.compare(m1, m2) < 0);
        assertTrue(comparator.compare(m2, m1) > 0);
        assertTrue(comparator.compare(m3, m1) < 0);
        assertEquals(0, comparator.compare(m1, m1));
    }

    @Test
    void testSortWithComparator() {
        Matrix m1 = new Matrix(2);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(1, 0, 3);
        m1.set(1, 1, 4);

        DiagMatrix m2 = new DiagMatrix(2, 3);

        UpTriangleMatrix m3 = new UpTriangleMatrix(2);
        m3.set(0, 0, 1);
        m3.set(0, 1, 5);
        m3.set(1, 1, 4);

        IMatrix[] matrices = { m2, m1, m3 };
        Arrays.sort(matrices, new MatrixComparator());

        assertArrayEquals(matrices, new IMatrix[]{m1, m3, m2});
    }
}
