package lab33;

import lab31.*;
import lab32.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoodsServiceTest {

    @Test
    void testCountByFilterDeepSimple() {
        WrappedGoodsInterface[] items = { new GoodsWrapper("Apple", 1.0) };
        GoodsBatch batch = new GoodsBatch("Test", items);
        Filter appleFilter = new BeginStringFilter("Apple");
        assertEquals(1, GoodsService.countByFilterDeep(batch, appleFilter));
    }

    @Test
    void testCountByFilterDeepNestedWrapped() {

        GoodsWrapper innerApple = new GoodsWrapper("Apple", 0.1);
        WrappedGoods nested = new WrappedGoods(new GoodsWrapper("Box", 0.2),
                new WrappedGoodsInterface[] { innerApple });
        GoodsBatch outerBatch = new GoodsBatch("Outer", new WrappedGoodsInterface[] { nested });

        Filter appleFilter = new BeginStringFilter("Apple");
        assertEquals(1, GoodsService.countByFilterDeep(outerBatch, appleFilter));
    }

    @Test
    void testCountByFilterDeepDeepNesting() {

        GoodsWrapper deepestApple = new GoodsWrapper("Apple", 0.05);
        WrappedGoods middle = new WrappedGoods(new GoodsWrapper("MiddleBox", 0.1),
                new WrappedGoodsInterface[] { deepestApple });
        GoodsBatch outer = new GoodsBatch("OuterBox", new WrappedGoodsInterface[] { middle });
        GoodsBatch batch = new GoodsBatch("Deep", new WrappedGoodsInterface[] { outer });

        Filter appleFilter = new BeginStringFilter("Apple");
        assertEquals(1, GoodsService.countByFilterDeep(batch, appleFilter));
    }

    @Test
    void testCountByFilterDeepMixedTypes() {
        WrappedGoodsInterface[] deepItems = {
                new GoodsWrapper("DirectMatch", 1.0),
                new WrappedPiecedGoods(new PiecedGoods("Apple", "Fruit", 0.5), 2, new GoodsWrapper("Pack", 0.1)),
                new WrappedWeightedGoods(new WeightedGoods("Banana", "Fruit"), 3.0, new GoodsWrapper("Bag", 0.2))
        };
        GoodsBatch batch = new GoodsBatch("Mixed", deepItems);
        Filter appleFilter = new BeginStringFilter("Apple");
        assertEquals(1, GoodsService.countByFilterDeep(batch, appleFilter));
    }

    @Test
    void testCountByFilterDeepEmptyNested() {
        WrappedGoods emptyNested = new WrappedGoods(new GoodsWrapper("EmptyBox", 0.1), new WrappedGoodsInterface[0]);
        GoodsBatch batch = new GoodsBatch("Test", new WrappedGoodsInterface[] { emptyNested });
        assertEquals(0, GoodsService.countByFilterDeep(batch, new BeginStringFilter("X")));
    }

    @Test
    void testCheckAllWeightedGoodsSimpleTrue() {
        WeightedGoods wg = new WeightedGoods("WeightOnly", "No pieces");
        WrappedWeightedGoods wrapped = new WrappedWeightedGoods(wg, 2.0, new GoodsWrapper("Bag", 0.1));
        GoodsBatch batch = new GoodsBatch("AllWeighted", new WrappedGoodsInterface[] { wrapped });
        assertTrue(GoodsService.checkAllWeightedGoods(batch));
    }

    @Test
    void testCheckAllWeightedGoodsNestedTrue() {

        WeightedGoods innerWg = new WeightedGoods("InnerWeight", "Nested");
        WrappedWeightedGoods innerWrapped = new WrappedWeightedGoods(innerWg, 1.0, new GoodsWrapper("InnerBag", 0.05));
        WrappedGoods outerWrapped = new WrappedGoods(new GoodsWrapper("Outer", 0.2),
                new WrappedGoodsInterface[] { innerWrapped });
        GoodsBatch batch = new GoodsBatch("Outer", new WrappedGoodsInterface[] { outerWrapped });
        assertTrue(GoodsService.checkAllWeightedGoods(batch));
    }

    @Test
    void testCheckAllWeightedGoodsWithPiecedFalse() {
        WrappedPiecedGoods pieced = new WrappedPiecedGoods(new PiecedGoods("Pieces", "Bad", 0.5), 3,
                new GoodsWrapper("Pack", 0.1));
        GoodsBatch batch = new GoodsBatch("Mixed", new WrappedGoodsInterface[] { pieced });
        assertFalse(GoodsService.checkAllWeightedGoods(batch));
    }

    @Test
    void testCheckAllWeightedGoodsNestedMixedFalse() {

        WrappedPiecedGoods badDeep = new WrappedPiecedGoods(new PiecedGoods("BadPieces", "", 0.1), 1,
                new GoodsWrapper("DeepPack", 0.01));
        WrappedGoods outer = new WrappedGoods(new GoodsWrapper("Container", 0.3),
                new WrappedGoodsInterface[] { badDeep });
        GoodsBatch batch = new GoodsBatch("Test", new WrappedGoodsInterface[] { outer });
        assertFalse(GoodsService.checkAllWeightedGoods(batch));
    }

    @Test
    void testCheckAllWeightedGoodsEmpty() {
        GoodsBatch empty = new GoodsBatch("Empty", new WrappedGoodsInterface[0]);
        assertTrue(GoodsService.checkAllWeightedGoods(empty));
    }

    @Test
    void testCheckAllWeightedGoodsOnlyWrappers() {
        GoodsWrapper wrapper = new GoodsWrapper("JustWrapper", 1.0);
        GoodsBatch batch = new GoodsBatch("Wrappers", new WrappedGoodsInterface[] { wrapper });
        assertTrue(GoodsService.checkAllWeightedGoods(batch));
    }

    @Test
    void testCountByFilterDeepNulls() {
        assertThrows(NullPointerException.class, () -> GoodsService.countByFilterDeep(null, new BeginStringFilter("")));
        GoodsWrapper nullName = new GoodsWrapper(null, 1.0);
        GoodsBatch batch = new GoodsBatch("Test", new WrappedGoodsInterface[] { nullName });
        assertThrows(NullPointerException.class,
                () -> GoodsService.countByFilterDeep(batch, new BeginStringFilter("X")));
    }

    @Test
    void testCheckAllWeightedGoodsNullBatch() {
        assertThrows(NullPointerException.class, () -> GoodsService.checkAllWeightedGoods(null));
    }

    @Test
    void testCountByFilterDeepNullFilter() {
        WrappedGoodsInterface[] items = { new GoodsWrapper("Test", 1.0) };
        GoodsBatch batch = new GoodsBatch("Test", items);
        assertThrows(NullPointerException.class, () -> GoodsService.countByFilterDeep(batch, null));
    }
}
