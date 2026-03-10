package ru.visoff;

public class DiagMatrix implements IMatrix {
    private final int size;
    private final double[] data;

    public DiagMatrix(int size) {
        this.size = size;
        if (size <= 0) throw new IllegalArgumentException("Размер матрицы должен быть положительным");
        this.data = new double[size];
    }

    public DiagMatrix(double... data) {
        this.size = data.length;
        if (size <= 0) throw new IllegalArgumentException("Размер матрицы должен быть положительным");
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    @Override
    public double get(int i, int j) {
        checkIndexes(i, j);
        return (i == j) ? data[i] : 0.0;
    }

    @Override
    public void set(int i, int j, double value) {
        checkIndexes(i, j);
        if (i == j) {
            data[i] = value;
        } else if (Math.abs(value) > 1e-10) {
            throw new IllegalArgumentException(
                String.format("Попытка записать ненулевое значение %.2f вне диагонали [%d, %d]", value, i, j)
            );
        }
    }

    @Override
    public double det() {
        double det = 1.0;
        for (int i = 0; i < size; i++) {
            det *= data[i];
        }
        
        return det;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DiagMatrix that = (DiagMatrix) obj;
        if (size != that.size) return false;
        
        for (int i = 0; i < size; i++) {
            if (Math.abs(data[i] - that.data[i]) > 1e-10) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        for (int i = 0; i < size; i++) {
            long bits = Double.doubleToLongBits(data[i]);
            result = 31 * result + (int)(bits ^ (bits >>> 32));
        }
        return result;
    }

    private void checkIndexes(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException(
                String.format("Индексы [%d, %d] выходят за границы матрицы размером %d", i, j, size)
            );
        }
    }
}
