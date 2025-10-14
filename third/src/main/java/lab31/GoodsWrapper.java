package lab31;

public class GoodsWrapper implements WrappedGoodsInterface {
    private String name;
    private double weight;

    public GoodsWrapper(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public GoodsWrapper(GoodsWrapper other) {
        this.name = new String(other.name);
        this.weight = other.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GoodsWrapper) {
            return name.equals(((GoodsWrapper) o).name) && weight == ((GoodsWrapper) o).weight;
        }
        return false;
    }

    @Override
    public String toString() {
        return "GoodsWrapper{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    public double getWeightNetto() {
        return 0;
    }

    public double getWeightBrutto() {
        return weight;
    }

	public String getName() {
		return name;
	}
	public double getWeight() {
		return weight;
	}
}
