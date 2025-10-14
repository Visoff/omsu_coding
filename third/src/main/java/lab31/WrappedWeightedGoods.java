package lab31;

public class WrappedWeightedGoods implements WrappedGoodsInterface {
    private GoodsWrapper wrapper;
    private WeightedGoods goods;
    private double goods_weight;

    public WrappedWeightedGoods(WeightedGoods goods, double goods_weight, GoodsWrapper wrapper) {
        this.wrapper = wrapper;
        this.goods = goods;
        this.goods_weight = goods_weight;
    }

    @Override
    public String toString() {
        return "WrappedWeightedGoods{" +
                "wrapper=" + wrapper +
                ", goods=" + goods +
                ", goods_weight=" + goods_weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WrappedWeightedGoods) {
            return  wrapper.equals(((WrappedWeightedGoods) o).wrapper) &&
                    goods.equals(((WrappedWeightedGoods) o).goods) &&
                    goods_weight == ((WrappedWeightedGoods) o).goods_weight;
        }
        return false;
    }

    public double getWeightNetto() {
        return goods_weight;
    }

    public double getWeightBrutto() {
        return goods_weight + wrapper.getWeight();
    }

	public GoodsWrapper getWrapper() {
		return wrapper;
	}
	public WeightedGoods getGoods() {
		return goods;
	}
	public double getGoods_weight() {
		return goods_weight;
	}

	@Override
	public String getName() {
        return this.wrapper.getName();
	}
}
