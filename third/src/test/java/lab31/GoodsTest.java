package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoodsTest {

    @Test
    public void testGoodsConstructorAndGetters() {
        Goods goods = new Goods("Test Goods", "Test Description");
        assertEquals("Test Goods", goods.getName());
        assertEquals("Test Description", goods.getDescription());
    }

    @Test
    public void testGoodsCopyConstructor() {
        Goods original = new Goods("Original", "Description");
        Goods copy = new Goods(original);
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
        assertNotSame(original.name, copy.name);
        assertNotSame(original.description, copy.description);
    }

    @Test
    public void testGoodsEquals() {
        Goods goods1 = new Goods("Name", "Desc");
        Goods goods2 = new Goods("Name", "Desc");
        Goods goods3 = new Goods("Different", "Desc");
        
        assertTrue(goods1.equals(goods2));
        assertFalse(goods1.equals(goods3));
        assertFalse(goods1.equals(null));
        assertFalse(goods1.equals("Not a Goods object"));
    }

    @Test
    public void testGoodsToString() {
        Goods goods = new Goods("Test", "Description");
        String result = goods.toString();
        assertTrue(result.contains("Test"));
        assertTrue(result.contains("Description"));
    }
}
