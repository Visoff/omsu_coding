package ru.visoff;

public class UpTriangleMatrix implements IMatrix {
    private final int size;
    private final double[] data;

    public UpTriangleMatrix(int size) {
        this.size = size;
        if (size <= 0) throw new IllegalArgumentException("Размер матрицы должен быть положительным");
        this.data = new double[size * (size + 1) / 2];
    }

    public int getSize() {
        return size;
    }

    @Override
    public double get(int i, int j) {
        checkIndexes(i, j);
        return (i <= j) ? data[getIndex(i, j)] : 0.0;
    }

    @Override
    public void set(int i, int j, double value) {
        checkIndexes(i, j);
        if (i <= j) {
            data[getIndex(i, j)] = value;
        } else if (Math.abs(value) > 1e-10) {
            throw new IllegalArgumentException(
                String.format("Попытка записать ненулевое значение %.2f в нижнетреугольную часть [%d, %d]", value, i, j)
            );
        }
    }

    @Override
    public double det() {
        double det = 1.0;
        for (int i = 0; i < size; i++) {
            det *= get(i, i);
        }
        
        return det;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UpTriangleMatrix that = (UpTriangleMatrix) obj;
        if (size != that.size) return false;
        
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (Math.abs(get(i, j) - that.get(i, j)) > 1e-10) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = size;
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                long bits = Double.doubleToLongBits(get(i, j));
                result = 31 * result + (int)(bits ^ (bits >>> 32));
            }
        }
        return result;
    }

    private int getIndex(int i, int j) {
        return i + j * (j + 1) / 2;
    }

    private void checkIndexes(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException(
                String.format("Индексы [%d, %d] выходят за границы матрицы размером %d", i, j, size)
            );
        }
    }
}
