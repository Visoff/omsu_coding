package lab31;

import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class BatchEdgeCasesTest {
    @Test
    public void testEmptyGoodsBatch() {
        GoodsBatch emptyBatch = new GoodsBatch("Empty", new WrappedGoodsInterface[0]);

        assertEquals("Empty", emptyBatch.getDescription());
        assertEquals(0, emptyBatch.getWrapped_goods().length);
        assertEquals(0.0, emptyBatch.getWeight(), 0.001);

        Iterator<WrappedGoodsInterface> iterator = emptyBatch.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    public void testGoodsBatchWithNullDescription() {
        WrappedGoodsInterface[] items = {
            new GoodsWrapper("Item1", 1.0)
        };
        
        GoodsBatch batch = new GoodsBatch(null, items);
        assertNull(batch.getDescription());
        assertEquals(1.0, batch.getWeight(), 0.001);
    }

    @Test
    public void testGoodsBatchWithNullItems() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GoodsBatch("Test", null);
        });
    }

    @Test
    public void testGoodsBatchWithMixedValidAndInvalid() {
        WrappedGoodsInterface[] mixedItems = {
            new WrappedPiecedGoods(
                new PiecedGoods("Valid", "Item", 1.0),
                1,
                new GoodsWrapper("Wrap", 0.1)
            ),
            null,
            new WrappedWeightedGoods(
                new WeightedGoods("Valid2", "Item2"),
                2.0,
                new GoodsWrapper("Wrap2", 0.2)
            )
        };

        GoodsBatch mixedBatch = new GoodsBatch("Mixed", mixedItems);

        assertThrows(NullPointerException.class, () -> {
            mixedBatch.getWeight();
        });
    }

    @Test
    public void testGoodsBatchIteratorModification() {
        WrappedGoodsInterface[] items = {
            new GoodsWrapper("Item1", 1.0),
            new GoodsWrapper("Item2", 2.0)
        };

        GoodsBatch batch = new GoodsBatch("Test", items);
        Iterator<WrappedGoodsInterface> iterator = batch.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(items[0], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(items[1], iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    public void testLargeGoodsBatch() {
        int largeSize = 1000;
        WrappedGoodsInterface[] largeArray = new WrappedGoodsInterface[largeSize];

        for (int i = 0; i < largeSize; i++) {
            largeArray[i] = new GoodsWrapper("Item" + i, i * 0.1);
        }

        GoodsBatch largeBatch = new GoodsBatch("Large Batch", largeArray);

        assertEquals(largeSize, largeBatch.getWrapped_goods().length);

        double expectedWeight = 0;
        for (int i = 0; i < largeSize; i++) {
            expectedWeight += i * 0.1;
        }

        assertEquals(expectedWeight, largeBatch.getWeight(), 0.001);

        int count = 0;
        for (WrappedGoodsInterface item : largeBatch) {
            assertNotNull(item);
            count++;
        }
        assertEquals(largeSize, count);
    }
}
