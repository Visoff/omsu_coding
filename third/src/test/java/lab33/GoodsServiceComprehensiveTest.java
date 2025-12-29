package lab33;

import lab31.*;
import lab32.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoodsServiceComprehensiveTest {

    @Test
    public void testCountByFilterWithVariousFilters() {
        WrappedGoodsInterface[] items = {
            new WrappedPiecedGoods(
                    new PiecedGoods("Apple", "Fruit", 0.2),
                    5,
                    new GoodsWrapper("FruitWrap", 0.1)
                    ),
            new WrappedWeightedGoods(
                    new WeightedGoods("Banana", "Yellow fruit"),
                    2.0,
                    new GoodsWrapper("BananaBag", 0.2)
                    ),
            new WrappedPiecedGoods(
                    new PiecedGoods("Apple Pie", "Dessert", 1.0),
                    2,
                    new GoodsWrapper("Box", 0.3)
                    ),
            new GoodsWrapper("Standalone", 0.5)
        };

        GoodsBatch batch = new GoodsBatch("Mixed Fruits", items);

        BeginStringFilter appleFilter = new BeginStringFilter("Apple");
        assertEquals(2, GoodsService.countByFilter(batch, appleFilter));

        EndStringFilter wrapFilter = new EndStringFilter("Wrap");
        assertEquals(0, GoodsService.countByFilter(batch, wrapFilter));

        BeginStringFilter noMatchFilter = new BeginStringFilter("XYZ");
        assertEquals(0, GoodsService.countByFilter(batch, noMatchFilter));

        BeginStringFilter allFilter = new BeginStringFilter("");
        assertEquals(4, GoodsService.countByFilter(batch, allFilter));
    }

    @Test
    public void testCountByFilterWithEmptyBatch() {
        GoodsBatch emptyBatch = new GoodsBatch("Empty", new WrappedGoodsInterface[0]);
        BeginStringFilter filter = new BeginStringFilter("");

        assertEquals(0, GoodsService.countByFilter(emptyBatch, filter));
    }

    @Test
    public void testCountByFilterWithNullBatch() {
        BeginStringFilter filter = new BeginStringFilter("test");

        assertThrows(NullPointerException.class, () -> {
            GoodsService.countByFilter(null, filter);
        });
    }

    @Test
    public void testCountByFilterWithNullFilter() {
        WrappedGoodsInterface[] items = {
            new GoodsWrapper("Item", 1.0)
        };
        GoodsBatch batch = new GoodsBatch("Test", items);

        assertThrows(NullPointerException.class, () -> {
            GoodsService.countByFilter(batch, null);
        });
    }

    @Test
    public void testCountByFilterWithItemsHavingNullNames() {
        GoodsWrapper nullNameWrapper = new GoodsWrapper(null, 1.0) {
            @Override
            public String getName() {
                return null;
            }
        };

        WrappedGoodsInterface[] items = {
            nullNameWrapper,
            new GoodsWrapper("Valid", 2.0)
        };

        GoodsBatch batch = new GoodsBatch("Mixed", items);
        BeginStringFilter filter = new BeginStringFilter("Valid");

        assertThrows(NullPointerException.class, () -> {
            GoodsService.countByFilter(batch, filter);
        });
    }

    @Test
    public void testCountByFilterPerformance() {
        // Create a large batch to test performance
        int size = 10000;
        WrappedGoodsInterface[] largeArray = new WrappedGoodsInterface[size];

        for (int i = 0; i < size; i++) {
            largeArray[i] = new GoodsWrapper("Item" + i, i * 0.1);
        }

        GoodsBatch largeBatch = new GoodsBatch("Large", largeArray);
        BeginStringFilter filter = new BeginStringFilter("Item");

        // This should complete without performance issues
        long startTime = System.currentTimeMillis();
        int count = GoodsService.countByFilter(largeBatch, filter);
        long endTime = System.currentTimeMillis();

        assertEquals(size, count);

        // Should complete in reasonable time (adjust threshold as needed)
        long maxAllowedTime = 1000; // 1 second
        assertTrue((endTime - startTime) < maxAllowedTime, 
                "Filtering took too long: " + (endTime - startTime) + "ms");
    }
}
