package lab;

public class FunctionC implements SingleArgumentFunctionFromRangeInterface {
    private double A;
    private double B;
    private double C;
    private double D;

    public FunctionC(double A, double B, double C, double D) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
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
        return (A * x + B) / (C * x + D);
	}
}
