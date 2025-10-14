package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WrappedPiecedGoodsTest {

    @Test
    public void testWrappedPiecedGoods() {
        PiecedGoods goods = new PiecedGoods("Apple", "Fresh apple", 0.2);
        GoodsWrapper wrapper = new GoodsWrapper("Plastic", 0.05);
        WrappedPiecedGoods wrapped = new WrappedPiecedGoods(goods, 10, wrapper);
        
        assertEquals("Apple", wrapped.getName());
        assertEquals(2.0, wrapped.getWeightNetto(), 0.001); // 10 * 0.2
        assertEquals(2.05, wrapped.getWeightBrutto(), 0.001); // 2.0 + 0.05
        assertEquals(goods, wrapped.getGoods());
        assertEquals(10, wrapped.getGoods_count());
        assertEquals(wrapper, wrapped.getWrapper());
    }
}
