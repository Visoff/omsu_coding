package ru.visoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for task 8: MatrixService.arrangeMatrices sorts the given array
 * of matrices by non-decreasing determinants.
 */
public class MatrixServiceTest {

    private static Matrix matrixWithDet(double det) {
        Matrix matrix = new Matrix(1);
        matrix.set(0, 0, det);
        return matrix;
    }

    @Test
    void arrangeMatrices_sortsByNonDecreasingDeterminants() {
        IMatrix[] data = {
                matrixWithDet(3.0),
                matrixWithDet(1.0),
                matrixWithDet(2.0)
        };

        MatrixService.arrangeMatrices(data);

        assertEquals(1.0, data[0].det());
        assertEquals(2.0, data[1].det());
        assertEquals(3.0, data[2].det());
    }

    @Test
    void arrangeMatrices_keepsEqualDeterminantsAdjacent() {
        IMatrix[] data = {
                matrixWithDet(2.0),
                matrixWithDet(2.0),
                matrixWithDet(1.0)
        };

        MatrixService.arrangeMatrices(data);

        assertEquals(1.0, data[0].det());
        assertEquals(2.0, data[1].det());
        assertEquals(2.0, data[2].det());
    }

    @Test
    void arrangeMatrices_alreadySortedUnchanged() {
        IMatrix[] data = {
                matrixWithDet(-1.0),
                matrixWithDet(0.0),
                matrixWithDet(4.0)
        };

        MatrixService.arrangeMatrices(data);

        assertEquals(-1.0, data[0].det());
        assertEquals(0.0, data[1].det());
        assertEquals(4.0, data[2].det());
    }

    @Test
    void arrangeMatrices_nullArrayIsNoOp() {
        assertDoesNotThrow(() -> MatrixService.arrangeMatrices(null));
    }

    @Test
    void arrangeMatrices_emptyArrayIsNoOp() {
        IMatrix[] data = new IMatrix[0];
        assertDoesNotThrow(() -> MatrixService.arrangeMatrices(data));
    }

    @Test
    void arrangeMatrices_withDiagonalAndTriangularMatrices() {
        IMatrix[] data = {
                new UpTriangleMatrix(2),
                new DiagMatrix(3.0, 2.0),
                new Matrix(2)
        };
        // UpTriangleMatrix(2): det = 0
        // Matrix(2): det = 0
        // DiagMatrix(3.0, 2.0): det = 6

        MatrixService.arrangeMatrices(data);

        assertEquals(0.0, data[0].det());
        assertEquals(0.0, data[1].det());
        assertEquals(6.0, data[2].det());
        assertTrue(data[2] instanceof DiagMatrix);
    }
}