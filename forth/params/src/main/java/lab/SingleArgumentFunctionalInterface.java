package lab;

public interface SingleArgumentFunctionalInterface<T extends SingleArgumentFunctionFromRangeInterface> {
    double apply(T f);
}
