package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

public class GoodsBatchTest {

    @Test
    public void testGoodsBatch() {
        WrappedGoodsInterface[] items = {
            new WrappedPiecedGoods(
                new PiecedGoods("Item1", "Desc1", 0.5), 
                3, 
                new GoodsWrapper("Wrap1", 0.1)
            ),
            new WrappedWeightedGoods(
                new WeightedGoods("Item2", "Desc2"),
                2.0,
                new GoodsWrapper("Wrap2", 0.2)
            )
        };
        
        GoodsBatch batch = new GoodsBatch("Test Batch", items);
        
        assertEquals("Test Batch", batch.getDescription());
        assertEquals(items, batch.getWrapped_goods());
        
        double expectedWeight = 
            (3 * 0.5 + 0.1) + 
            (2.0 + 0.2);      
        assertEquals(expectedWeight, batch.getWeight(), 0.001);
    }

    @Test
    public void testIterator() {
        WrappedGoodsInterface[] items = {
            new GoodsWrapper("Wrapper1", 0.1),
            new GoodsWrapper("Wrapper2", 0.2)
        };
        
        GoodsBatch batch = new GoodsBatch("Test", items);
        Iterator<WrappedGoodsInterface> iterator = batch.iterator();
        
        assertTrue(iterator.hasNext());
        assertEquals(items[0], iterator.next());
        assertEquals(items[1], iterator.next());
        assertFalse(iterator.hasNext());
    }
}
