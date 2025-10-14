package lab31;

public class WrappedPiecedGoods implements WrappedGoodsInterface {
    private PiecedGoods goods;
    private int goods_count;
    private GoodsWrapper wrapper;

    public WrappedPiecedGoods(PiecedGoods goods, int goods_count, GoodsWrapper wrapper) {
        this.goods = goods;
        this.goods_count = goods_count;
        this.wrapper = wrapper;
    }

    @Override
    public String toString() {
        return "WrappedPiecedGoods{" +
                "goods=" + goods +
                ", goods_count=" + goods_count +
                ", wrapper=" + wrapper +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WrappedPiecedGoods) {
            return  wrapper.equals(((WrappedPiecedGoods) o).wrapper) &&
                    goods.equals(((WrappedPiecedGoods) o).goods) &&
                    goods_count == ((WrappedPiecedGoods) o).goods_count;
        }
        return false;
    }

    public double getWeightNetto() {
        return goods_count * goods.getWeight();
    }

    public double getWeightBrutto() {
        return getWeightNetto() + wrapper.getWeight();
    }

	public PiecedGoods getGoods() {
		return goods;
	}
	public int getGoods_count() {
		return goods_count;
	}
	public GoodsWrapper getWrapper() {
		return wrapper;
	}

	@Override
	public String getName() {
        return goods.getName();
	}
}
