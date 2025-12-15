package lab;

public class Integral<T extends SingleArgumentFunctionFromRangeInterface> implements SingleArgumentFunctionalInterface<T> {
    private double A;
    private double B;

    public Integral(double A, double B) {
        this.A = A;
        this.B = B;
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
        for (double i = A; i < B; i += 0.001) {
            res += f.apply(i) * 0.001;
        }
        return res;
	}
}
