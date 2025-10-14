package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WrappedWeightedGoodsTest {

    @Test
    public void testWrappedWeightedGoods() {
        WeightedGoods goods = new WeightedGoods("Rice", "Basmati rice");
        GoodsWrapper wrapper = new GoodsWrapper("Bag", 0.1);
        WrappedWeightedGoods wrapped = new WrappedWeightedGoods(goods, 2.5, wrapper);
        
        assertEquals("Bag", wrapped.getName());
        assertEquals(2.5, wrapped.getWeightNetto(), 0.001);
        assertEquals(2.6, wrapped.getWeightBrutto(), 0.001);
        assertEquals(goods, wrapped.getGoods());
        assertEquals(2.5, wrapped.getGoods_weight(), 0.001);
        assertEquals(wrapper, wrapped.getWrapper());
    }
}
