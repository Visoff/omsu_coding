package lab31;

public class WeightedGoods extends Goods {

    public WeightedGoods(String name, String description) {
        super(name, description);
    }

	public WeightedGoods(WeightedGoods other) {
		super(other);
	}

    @Override
    public String toString() {
        return "WeightedGoods{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
