package lab31;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class WeightCalculationTest {

    @Test
    @DisplayName("Test weight calculations with various precision values")
    public void testPrecisionWeightCalculations() {
        // Test with very small weights
        WrappedPiecedGoods smallWeights = new WrappedPiecedGoods(
            new PiecedGoods("Micro", "Tiny items", 0.001),
            1000,
            new GoodsWrapper("MicroWrap", 0.0001)
        );
        
        assertEquals(1.0, smallWeights.getWeightNetto(), 0.0001); // 1000 * 0.001
        assertEquals(1.0001, smallWeights.getWeightBrutto(), 0.0001);
        
        // Test with very large weights
        WrappedWeightedGoods largeWeights = new WrappedWeightedGoods(
            new WeightedGoods("Heavy", "Very heavy item"),
            1000000.0,
            new GoodsWrapper("HeavyWrap", 50.0)
        );
        
        assertEquals(1000000.0, largeWeights.getWeightNetto(), 0.001);
        assertEquals(1000050.0, largeWeights.getWeightBrutto(), 0.001);
    }

    @Test
    @DisplayName("Test cumulative weight calculation accuracy")
    public void testCumulativeWeightAccuracy() {
        // Create multiple items with weights that sum to a precise value
        WrappedGoodsInterface[] preciseItems = new WrappedGoodsInterface[10];
        double expectedTotal = 0.0;
        
        for (int i = 0; i < 10; i++) {
            double weight = 0.1 * (i + 1);
            preciseItems[i] = new GoodsWrapper("Item" + i, weight);
            expectedTotal += weight;
        }
        
        GoodsBatch preciseBatch = new GoodsBatch("Precise", preciseItems);
        assertEquals(expectedTotal, preciseBatch.getWeight(), 0.0000001);
    }

    @Test
    @DisplayName("Test weight calculations with zero and negative values")
    public void testZeroAndNegativeWeights() {
        // Zero weight wrapper
        GoodsWrapper zeroWrapper = new GoodsWrapper("Zero", 0.0);
        assertEquals(0.0, zeroWrapper.getWeightBrutto(), 0.001);
        
        // Negative weight wrapper
        GoodsWrapper negativeWrapper = new GoodsWrapper("Negative", -1.0);
        assertEquals(-1.0, negativeWrapper.getWeightBrutto(), 0.001);
        
        // Mixed positive and negative weights in batch
        WrappedGoodsInterface[] mixedWeights = {
            new GoodsWrapper("Positive", 5.0),
            new GoodsWrapper("Negative", -2.0),
            new GoodsWrapper("Zero", 0.0)
        };
        
        GoodsBatch mixedBatch = new GoodsBatch("Mixed Weights", mixedWeights);
        assertEquals(3.0, mixedBatch.getWeight(), 0.001); // 5.0 - 2.0 + 0.0
    }

    @Test
    @DisplayName("Test weight calculation consistency across multiple calls")
    public void testWeightCalculationConsistency() {
        WrappedPiecedGoods wrappedGoods = new WrappedPiecedGoods(
            new PiecedGoods("Consistent", "Item", 2.5),
            4,
            new GoodsWrapper("ConsistentWrap", 0.5)
        );
        
        double firstCall = wrappedGoods.getWeightBrutto();
        double secondCall = wrappedGoods.getWeightBrutto();
        double thirdCall = wrappedGoods.getWeightBrutto();
        
        assertEquals(firstCall, secondCall, 0.001);
        assertEquals(secondCall, thirdCall, 0.001);
        assertEquals(firstCall, thirdCall, 0.001);
    }
}
