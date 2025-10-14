package lab31;

public class PiecedGoods extends Goods {
    private double weight;

    public PiecedGoods(String name, String description, double weight) {
        super(name, description);
        this.weight = weight;
    }

    public PiecedGoods(PiecedGoods other) {
        super(other);
        this.weight = other.weight;
    }

    @Override
    public String toString() {
        return "PiecedGoods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PiecedGoods) {
            return  name.equals(((PiecedGoods) o).name) &&
                    description.equals(((PiecedGoods) o).description) &&
                    weight == ((PiecedGoods) o).weight;
        }
        return false;
    }

    public double getWeight() {
        return weight;
    }
}
