package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WrappedGoodsTest {

    @Test
    public void testWrappedGoods() {
        GoodsWrapper innerWrapper = new GoodsWrapper("Inner", 0.1);
        GoodsWrapper outerWrapper = new GoodsWrapper("Outer", 0.2);
        
        WrappedPiecedGoods innerGoods = new WrappedPiecedGoods(
            new PiecedGoods("Chocolate", "Sweet", 0.1), 
            5, 
            innerWrapper
        );
        
        WrappedGoodsInterface[] contents = {innerGoods};
        WrappedGoods wrapped = new WrappedGoods(outerWrapper, contents);
        
        double expectedNetto = innerGoods.getWeightBrutto(); // 5 * 0.1 + 0.1 = 0.6
        double expectedBrutto = expectedNetto + outerWrapper.getWeightBrutto(); // 0.6 + 0.2 = 0.8
        
        assertEquals(expectedNetto, wrapped.getWeightNetto(), 0.001);
        assertEquals(expectedBrutto, wrapped.getWeightBrutto(), 0.001);
        assertEquals(contents, wrapped.getWrapped_goods());
        assertEquals(outerWrapper, wrapped.getWrapper());
    }
}
