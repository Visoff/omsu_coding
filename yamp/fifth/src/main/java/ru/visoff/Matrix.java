package ru.visoff;

public class Matrix implements IMatrix {
    private final int size;
    private final double[] data;
    private Double detCache = null;
    private boolean detCached = false;

    public Matrix(int size) {
        this.size = size;
        if (size <= 0)
            throw new IllegalArgumentException("Размер матрицы должен быть положительным");
        this.data = new double[size * size];
    }

    public int getSize() {
        return size;
    }

    @Override
    public double get(int i, int j) {
        checkIndexes(i, j);
        return data[i * size + j];
    }

    @Override
    public void set(int i, int j, double value) {
        checkIndexes(i, j);
        if (data[i * size + j] != value) {
            data[i * size + j] = value;
            detCached = false;
            detCache = null;
        }
    }

    @Override
    public double det() {
        if (detCached && detCache != null) {
            return detCache;
        }

        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(data, i * size, matrix[i], 0, size);
        }

        double det = 1.0;
        int swapCount = 0;

        for (int i = 0; i < size; i++) {
            int maxRow = i;
            for (int k = i + 1; k < size; k++) {
                if (Math.abs(matrix[k][i]) > Math.abs(matrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            if (maxRow != i) {
                double[] temp = matrix[i];
                matrix[i] = matrix[maxRow];
                matrix[maxRow] = temp;
                swapCount++;
                det *= -1;
            }

            if (Math.abs(matrix[i][i]) < 1e-10) {
                detCache = 0.0;
                detCached = true;
                return 0.0;
            }

            det *= matrix[i][i];

            for (int k = i + 1; k < size; k++) {
                double factor = matrix[k][i] / matrix[i][i];
                for (int j = i; j < size; j++) {
                    matrix[k][j] -= factor * matrix[i][j];
                }
            }
        }

        detCache = det;
        detCached = true;
        return det;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Matrix matrix = (Matrix) obj;
        if (size != matrix.size)
            return false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(get(i, j) - matrix.get(i, j)) > 1e-10) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        for (int i = 0; i < data.length; i++) {
            long bits = Double.doubleToLongBits(data[i]);
            result = 31 * result + (int) (bits ^ (bits >>> 32));
        }
        return result;
    }

    private void checkIndexes(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Индексы [%d, %d] выходят за границы матрицы размером %d", i, j, size));
        }
    }
}
