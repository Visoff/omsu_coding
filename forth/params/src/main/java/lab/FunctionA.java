package lab;

public class FunctionA implements SingleArgumentFunctionFromRangeInterface {
    private double A;
    private double B;

    public FunctionA(double A, double B) {
        this.A = A;
        this.B = B;
    }

	@Override
	public double getLowerBound() {
        return Double.NEGATIVE_INFINITY;
	}

	@Override
	public double getUpperBound() {
        return Double.POSITIVE_INFINITY;
	}

	@Override
	public double apply(double x) {
        return A * x + B;
	}
}
