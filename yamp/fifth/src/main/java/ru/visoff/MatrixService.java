package ru.visoff;

import java.util.Arrays;

public class MatrixService {
    public static void sortMatrixes(IMatrix[] data) {
        if (data == null) return;
        Arrays.sort(data, new MatrixComparator());
    }

    /**
     * Task 8: sorts the given array of matrices by non-decreasing determinants,
     * using the appropriate sorting method from java.util.Arrays.
     */
    public static void arrangeMatrices(IMatrix[] data) {
        sortMatrixes(data);
    }
}
