package lab31;

import java.util.Iterator;

public class GoodsBatch implements Iterable<WrappedGoodsInterface>, WrappedGoodsInterface {
    private String description;
    private WrappedGoodsInterface[] wrapped_goods;

    public GoodsBatch(String description, WrappedGoodsInterface[] wrapped_goods) {
        if (wrapped_goods == null) {
            throw new IllegalArgumentException("Wrapped goods cannot be null");
        }
        this.description = description;
        this.wrapped_goods = wrapped_goods;
    }

    @Override
    public String toString() {
        return "GoodsBatch{" +
                "description='" + description + '\'' +
                ", wrapped_goods=" + wrapped_goods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GoodsBatch) {
            return description.equals(((GoodsBatch) o).description) &&
                    wrapped_goods.equals(((GoodsBatch) o).wrapped_goods);
        }
        return false;
    }

    public double getWeight() {
        double weight = 0;
        for (WrappedGoodsInterface wrappedGood : wrapped_goods) {
            weight += wrappedGood.getWeightBrutto();
        }
        return weight;
    }

	public String getDescription() {
		return description;
	}
	public WrappedGoodsInterface[] getWrapped_goods() {
		return wrapped_goods;
	}

	@Override
	public Iterator<WrappedGoodsInterface> iterator() {
        return java.util.Arrays.asList(this.wrapped_goods).iterator();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getName'");
	}

	@Override
	public double getWeightNetto() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getWeightNetto'");
	}

	@Override
	public double getWeightBrutto() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getWeightBrutto'");
	}
}
