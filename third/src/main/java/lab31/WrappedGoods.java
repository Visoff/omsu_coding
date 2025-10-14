package lab31;

public class WrappedGoods implements WrappedGoodsInterface {
    private WrappedGoodsInterface[] wrapped_goods;
    private GoodsWrapper wrapper;

    public WrappedGoods(GoodsWrapper wrapper, WrappedGoodsInterface[] wrapped_goods) {
        if (wrapped_goods == null) {
            throw new IllegalArgumentException("Wrapped goods cannot be null");
        }
        this.wrapped_goods = wrapped_goods;
        this.wrapper = wrapper;
    }

    @Override
    public String toString() {
        return "WrappedGoods{" +
                "wrapped_goods=" + wrapped_goods +
                ", wrapper=" + wrapper +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WrappedGoods) {
            return  wrapper.equals(((WrappedGoods) o).wrapper) &&
                    wrapped_goods.equals(((WrappedGoods) o).wrapped_goods);
        }
        return false;
    }

    public double getWeightNetto() {
        double weight = 0;
        for (WrappedGoodsInterface wrappedGood : wrapped_goods) {
            weight += wrappedGood.getWeightBrutto();
        }
        return weight;
    }

    public double getWeightBrutto() {
        double weight = 0;
        for (WrappedGoodsInterface wrappedGood : wrapped_goods) {
            weight += wrappedGood.getWeightBrutto();
        }
        return weight + wrapper.getWeightBrutto();
    }

    public WrappedGoodsInterface[] getWrapped_goods() {
        // TODO: copy
        return wrapped_goods;
    }
    public GoodsWrapper getWrapper() {
        return wrapper;
    }

	@Override
	public String getName() {
        String[] res = new String[wrapped_goods.length];
        for (int i = 0; i < wrapped_goods.length; i++) {
            res[i] = wrapped_goods[i].getName();
        }
        return String.join(", ", res);
	}
}
