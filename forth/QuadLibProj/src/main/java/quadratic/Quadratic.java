package quadratic;

// ax^2 + bx + c == 0
public class Quadratic {
    private double a, b, c;

    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean areInfiniteRoots() {
        return a == 0 && b == 0 && c == 0;
    }

    public double[] getRoots() {
        if (a == 0) {
            if (b == 0) {
                return new double[0];
            }
            return new double[]{-c / b};
        }
        double d = b * b - 4 * a * c;
        if (d < 0) {
            return new double[0];
        }
        if (d == 0) {
            return new double[]{-b / (2 * a)};
        }
        return new double[]{-b / (2 * a) + Math.sqrt(d) / (2 * a), -b / (2 * a) - Math.sqrt(d) / (2 * a)};
    }
}
