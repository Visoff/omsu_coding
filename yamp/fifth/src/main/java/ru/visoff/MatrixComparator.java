package ru.visoff;

import java.util.Comparator;

public class MatrixComparator implements Comparator<IMatrix> {
    public int compare(IMatrix m1, IMatrix m2) {
        return Double.compare(m1.det(), m2.det());
    }
}
