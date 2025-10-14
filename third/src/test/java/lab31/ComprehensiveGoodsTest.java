package lab31;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ComprehensiveGoodsTest {
    private Goods basicGoods;

    @BeforeEach
    public void setUp() {
        basicGoods = new Goods("Test Goods", "Test Description");
    }

    @Test
    public void testGoodsWithNullValues() {
        Goods nullGoods = new Goods(null, null);
        assertNull(nullGoods.getName());
        assertNull(nullGoods.getDescription());
    }

    @Test
    public void testGoodsWithEmptyStrings() {
        Goods emptyGoods = new Goods("", "");
        assertEquals("", emptyGoods.getName());
        assertEquals("", emptyGoods.getDescription());
    }

    @Test
    public void testGoodsEqualityWithNull() {
        assertFalse(basicGoods.equals(null));
    }

    @Test
    public void testGoodsEqualityWithDifferentClass() {
        assertFalse(basicGoods.equals("Not a Goods object"));
        assertFalse(basicGoods.equals(new Object()));
    }

    @Test
    public void testGoodsashCodeConsistency() {
        Goods goods1 = new Goods("Name", "Description");
        Goods goods2 = new Goods("Name", "Description");
        assertEquals(goods1.hashCode(), goods2.hashCode());
    }

    @Test
    public void testPiecedGoodsWithZeroWeight() {
        PiecedGoods zeroWeight = new PiecedGoods("Zero", "Zero weight", 0.0);
        assertEquals(0.0, zeroWeight.getWeight(), 0.001);
    }

    @Test
    public void testPiecedGoodsWithNegativeWeight() {
        PiecedGoods negativeWeight = new PiecedGoods("Negative", "Negative weight", -5.0);
        assertEquals(-5.0, negativeWeight.getWeight(), 0.001);
    }

    @Test
    public void testWeightedGoodsCopyConstructorWithNull() {
        WeightedGoods original = null;
        assertThrows(NullPointerException.class, () -> {
            WeightedGoods copy = new WeightedGoods(original);
        });
    }
}
