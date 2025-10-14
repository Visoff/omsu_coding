package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeightedGoodsTest {

    @Test
    public void testWeightedGoodsConstructor() {
        WeightedGoods wg = new WeightedGoods("Weighted Item", "Description");
        assertEquals("Weighted Item", wg.getName());
        assertEquals("Description", wg.getDescription());
    }

    @Test
    public void testWeightedGoodsCopyConstructor() {
        WeightedGoods original = new WeightedGoods("Original", "Desc");
        WeightedGoods copy = new WeightedGoods(original);
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
    }
}
