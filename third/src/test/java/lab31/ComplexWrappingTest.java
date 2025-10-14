package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexWrappingTest {

    @Test
    public void testDeeplyNestedWrappedGoods() {
        // Create a complex nested structure
        GoodsWrapper innerWrapper = new GoodsWrapper("Inner", 0.1);
        GoodsWrapper middleWrapper = new GoodsWrapper("Middle", 0.2);
        GoodsWrapper outerWrapper = new GoodsWrapper("Outer", 0.3);

        // Innermost: Pieced goods
        WrappedPiecedGoods innerPieced = new WrappedPiecedGoods(
            new PiecedGoods("Candy", "Sweet candy", 0.05),
            10,
            innerWrapper
        );

        // Middle: Wrap the pieced goods with weighted goods
        WrappedWeightedGoods middleWeighted = new WrappedWeightedGoods(
            new WeightedGoods("Bulk", "Bulk items"),
            1.5,
            middleWrapper
        );

        // Outer: Combine both in a WrappedGoods
        WrappedGoodsInterface[] contents = {innerPieced, middleWeighted};
        WrappedGoods outerWrapped = new WrappedGoods(outerWrapper, contents);

        // Calculate expected weights
        double innerBrutto = innerPieced.getWeightBrutto(); // 10*0.05 + 0.1 = 0.6
        double middleBrutto = middleWeighted.getWeightBrutto(); // 1.5 + 0.2 = 1.7
        double expectedNetto = innerBrutto + middleBrutto; // 0.6 + 1.7 = 2.3
        double expectedBrutto = expectedNetto + outerWrapper.getWeightBrutto(); // 2.3 + 0.3 = 2.6

        assertEquals(expectedNetto, outerWrapped.getWeightNetto(), 0.001);
        assertEquals(expectedBrutto, outerWrapped.getWeightBrutto(), 0.001);
    }

    @Test
    public void testEmptyWrappedGoods() {
        GoodsWrapper wrapper = new GoodsWrapper("Empty", 0.1);
        WrappedGoodsInterface[] emptyContents = {};
        WrappedGoods emptyWrapped = new WrappedGoods(wrapper, emptyContents);

        assertEquals(0.0, emptyWrapped.getWeightNetto(), 0.001);
        assertEquals(0.1, emptyWrapped.getWeightBrutto(), 0.001);
        assertEquals("", emptyWrapped.getName());
    }

    @Test
    public void testWrappedGoodsWithNullArray() {
        GoodsWrapper wrapper = new GoodsWrapper("Null", 0.1);
        assertThrows(IllegalArgumentException.class, () -> {
            WrappedGoods wrapped = new WrappedGoods(wrapper, null);
        });
    }

    @Test
    public void testWrappedGoodsWithNullElements() {
        GoodsWrapper wrapper = new GoodsWrapper("Mixed", 0.1);
        WrappedGoodsInterface[] mixedContents = {
            new WrappedPiecedGoods(
                new PiecedGoods("Item1", "Desc1", 1.0),
                2,
                new GoodsWrapper("Wrap1", 0.1)
            ),
            null,
            new WrappedWeightedGoods(
                new WeightedGoods("Item2", "Desc2"),
                3.0,
                new GoodsWrapper("Wrap2", 0.2)
            )
        };

        WrappedGoods mixedWrapped = new WrappedGoods(wrapper, mixedContents);
        
        // This will throw NullPointerException when calculating weights
        assertThrows(NullPointerException.class, () -> {
            mixedWrapped.getWeightNetto();
        });
    }

    @Test
    public void testMultipleLevelsOfWrapping() {
        // Level 1: Individual items
        WrappedPiecedGoods item1 = new WrappedPiecedGoods(
            new PiecedGoods("Book", "Hardcover", 1.0),
            1,
            new GoodsWrapper("Plastic", 0.1)
        );

        WrappedPiecedGoods item2 = new WrappedPiecedGoods(
            new PiecedGoods("Toy", "Plastic toy", 0.5),
            2,
            new GoodsWrapper("Bubble", 0.05)
        );

        // Level 2: Box containing items
        WrappedGoodsInterface[] boxContents = {item1, item2};
        WrappedGoods box = new WrappedGoods(
            new GoodsWrapper("Cardboard", 0.3),
            boxContents
        );

        // Level 3: Pallet containing boxes
        WrappedGoodsInterface[] palletContents = {box, box}; // Two identical boxes
        WrappedGoods pallet = new WrappedGoods(
            new GoodsWrapper("Wood", 5.0),
            palletContents
        );

        // Calculate total weight
        double item1Weight = item1.getWeightBrutto(); // 1*1.0 + 0.1 = 1.1
        double item2Weight = item2.getWeightBrutto(); // 2*0.5 + 0.05 = 1.05
        double boxWeight = box.getWeightBrutto(); // (1.1 + 1.05) + 0.3 = 2.45
        double expectedPalletWeight = (boxWeight * 2) + 5.0; // 2.45*2 + 5.0 = 9.9

        assertEquals(expectedPalletWeight, pallet.getWeightBrutto(), 0.001);
    }
}
