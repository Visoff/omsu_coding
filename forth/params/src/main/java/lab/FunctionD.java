package lab;

public class FunctionD implements SingleArgumentFunctionFromRangeInterface {
    private double A;
    private double B;

    public FunctionD(double A, double B) {
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
        return A*Math.exp(x) + B;
	}
}
