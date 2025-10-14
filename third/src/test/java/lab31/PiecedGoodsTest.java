package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PiecedGoodsTest {

    @Test
    public void testPiecedGoodsConstructor() {
        PiecedGoods pg = new PiecedGoods("Item", "Description", 2.5);
        assertEquals("Item", pg.getName());
        assertEquals("Description", pg.getDescription());
        assertEquals(2.5, pg.getWeight(), 0.001);
    }

    @Test
    public void testPiecedGoodsCopyConstructor() {
        PiecedGoods original = new PiecedGoods("Original", "Desc", 1.5);
        PiecedGoods copy = new PiecedGoods(original);
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getWeight(), copy.getWeight(), 0.001);
    }

    @Test
    public void testPiecedGoodsEquals() {
        PiecedGoods pg1 = new PiecedGoods("Item", "Desc", 1.0);
        PiecedGoods pg2 = new PiecedGoods("Item", "Desc", 1.0);
        PiecedGoods pg3 = new PiecedGoods("Different", "Desc", 1.0);
        
        assertTrue(pg1.equals(pg2));
        assertFalse(pg1.equals(pg3));
    }
}
