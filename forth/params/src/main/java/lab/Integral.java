package lab;

public class Integral<T extends SingleArgumentFunctionFromRangeInterface> implements SingleArgumentFunctionalInterface<T> {
    private double A;
    private double B;
    private double persision = 1e-3;

    public Integral(double A, double B) {
        this.A = A;
        this.B = B;
    }

    public Integral(double A, double B, double persision) {
        this.A = A;
        this.B = B;
        this.persision = persision;
    }

	@Override
	public double apply(T f) {
        if (f.getLowerBound() > A) {
            A = f.getLowerBound();
        }
        if (f.getUpperBound() < B) {
            B = f.getUpperBound();
        }
        double res = 0;
        double dx = persision;
        for (double x = A; x < B; x += dx) {
            res += f.apply(x) * dx;
        }
        return res;
	}

    public Integral<T> withPrecision(double persision) {
        return new Integral<>(A, B, persision);
    }
}
