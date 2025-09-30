import java.util.ArrayList;
import java.util.List;

public class Main_15 {
    public static void main(String[] args) {
        Vector3DArray vectors = new Vector3DArray(3);
        vectors.set(0, new Vector3D(1, 2, 3));
        vectors.set(1, new Vector3D(4, 5, 6));
        vectors.set(2, new Vector3D(7, 8, 9));
        System.out.println(vectors.sum());
        System.out.println(vectors.maxLength());
        System.out.println(vectors.find(new Vector3D(4, 5, 6)));
        System.out.println(vectors.possibleShifts(new Point3D(1, -1, 5)));
        System.out.println(vectors.sumWithCoefficients(List.of(1.0, 0.0, -1.0)));
    }
}

class Point3D {
    public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point3D)) {
            return false;
        }
        Point3D other = (Point3D) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

	private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D() {
        this(0, 0, 0);
    }

    public String toString() {
        return String.format("Point3D(%f, %f, %f)", x, y, z);
    }
}

class Vector3D {
    private double x;
	private double y;
    private double z;

    public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
    public Vector3D() {
        this(0, 0, 0);
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Point3D start, Point3D end) {
        this.x = end.getX() - start.getX();
        this.y = end.getY() - start.getY();
        this.z = end.getZ() - start.getZ();
    }

    public Double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3D)) {
            return false;
        }
        Vector3D other = (Vector3D) obj;
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    public String toString() {
        return String.format("Vector3D(%f, %f, %f)", x, y, z);
    }
}

class Vector3DProcessor {
    public static Vector3D scale(Vector3D vector, Double scalar) {
        return new Vector3D(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    public static Vector3D add(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    public static Vector3D sub(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }

    public static Double dot(Vector3D v1, Vector3D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public static Vector3D cross(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.getY() * v2.getZ() - v1.getZ() * v2.getY(), v1.getZ() * v2.getX() - v1.getX() * v2.getZ(), v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    public static Boolean areColeinear(Vector3D v1, Vector3D v2) {
        return dot(v1, v2) != 0;
    }
}

class Vector3DArray {
    private List<Vector3D> vectors;

    public Vector3DArray(Integer n) {
        this.vectors = new ArrayList<Vector3D>();
        for (int i = 0; i < n; i++) {
            this.vectors.add(new Vector3D());
        }
    }

    public Integer size() {
        return this.vectors.size();
    }

    public void set(Integer index, Vector3D vector) {
        this.vectors.set(index, vector);
    }

    public Double maxLength() {
        Double max = 0.0;
        for (Vector3D vector : this.vectors) {
            if (vector.length() > max) {
                max = vector.length();
            }
        }
        return max;
    }

    public Integer find(Vector3D vector) {
        for (int i = 0; i < this.vectors.size(); i++) {
            if (this.vectors.get(i).equals(vector)) {
                return i;
            }
        }
        return -1;
    }

    public Vector3D sum() {
        Vector3D sum = new Vector3D();
        for (Vector3D vector : this.vectors) {
            sum = Vector3DProcessor.add(sum, vector);
        }
        return sum;
    }

    public Vector3D sumWithCoefficients(List<Double> coefficients) throws IllegalArgumentException {
        if (coefficients.size() != this.vectors.size()) {
            throw new IllegalArgumentException();
        }
        Vector3D sum = new Vector3D();
        for (int i = 0; i < this.vectors.size(); i++) {
            sum = Vector3DProcessor.add(sum, Vector3DProcessor.scale(this.vectors.get(i), coefficients.get(i)));
        }
        return sum;
    }

    public List<Point3D> possibleShifts(Point3D start) {
        List<Point3D> points = new ArrayList<Point3D>();
        for (Vector3D vector : this.vectors) {
            points.add(new Point3D(start.getX() + vector.getX(), start.getY() + vector.getY(), start.getZ() + vector.getZ()));
        }
        return points;
    }
}
