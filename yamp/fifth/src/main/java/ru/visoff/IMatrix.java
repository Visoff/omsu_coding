package ru.visoff;

public interface IMatrix {
    double get(int i, int j) throws IndexOutOfBoundsException;
    void set(int i, int j, double value) throws IndexOutOfBoundsException;
    double det();
};
