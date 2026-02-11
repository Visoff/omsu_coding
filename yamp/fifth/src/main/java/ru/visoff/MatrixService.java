package ru.visoff;

import java.util.Arrays;

public class MatrixService {
    public static void sortMatrixes(IMatrix[] data) {
        if (data == null) return;
        Arrays.sort(data, new MatrixComparator());
    }
}
