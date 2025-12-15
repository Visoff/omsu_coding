package lab;

public class SumAtEndsAndMiddle<T extends SingleArgumentFunctionFromRangeInterface> implements SingleArgumentFunctionalInterface<T> {
    @Override
    public double apply(T f) {
        double lower = f.apply(f.getLowerBound());
        double upper = f.apply(f.getUpperBound());
        double middle = f.apply((f.getLowerBound() + f.getUpperBound()) / 2);
        return lower + middle + upper;
    }
}
