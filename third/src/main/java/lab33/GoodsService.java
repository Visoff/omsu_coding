package lab33;

import lab31.GoodsBatch;
import lab31.GoodsWrapper;
import lab31.WrappedGoods;
import lab31.WrappedGoodsInterface;
import lab31.WrappedPiecedGoods;
import lab32.Filter;

public class GoodsService {
    public static int countByFilter(GoodsBatch batch, Filter filter) {
        int count = 0;
        for (WrappedGoodsInterface wrappedGood : batch) {
            if (filter.apply(wrappedGood.getName())) {
                count++;
            }
        }
        return count;
    }

    public static int countByFilterDeep(GoodsBatch batch, Filter filter) {
        int count = 0;
        for (WrappedGoodsInterface wrappedGood : batch) {
            if (wrappedGood instanceof GoodsBatch) {
                count += Math.min(1, countByFilterDeep((GoodsBatch) wrappedGood, filter));
            } else if (wrappedGood instanceof WrappedGoods) {
                WrappedGoods wg = (WrappedGoods) wrappedGood;
                count += Math.min(
                    1,
                    countByFilterDeep(new GoodsBatch("", wg.getWrapped_goods()), filter)
                );
            } else if (filter.apply(wrappedGood.getName())) {
                count++;
            }
        }
        return count;
    }

    public static boolean checkAllWeighted(GoodsBatch batch) {
        for (WrappedGoodsInterface wrappedGood : batch) {
            if (wrappedGood instanceof GoodsBatch) {
                if (!checkAllWeighted((GoodsBatch) wrappedGood)) {
                    return false;
                }
            } else if (wrappedGood instanceof WrappedGoods) {
                WrappedGoods wg = (WrappedGoods) wrappedGood;
                if (!checkAllWeighted(new GoodsBatch("", wg.getWrapped_goods()))) {
                    return false;
                }
            } else if (
                wrappedGood instanceof WrappedPiecedGoods || wrappedGood instanceof GoodsWrapper
            ) {
                return false;
            }
        }
        return true;
    }
}
