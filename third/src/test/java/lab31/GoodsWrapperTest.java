package lab31;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoodsWrapperTest {

    @Test
    public void testGoodsWrapperConstructor() {
        GoodsWrapper wrapper = new GoodsWrapper("Wrapper", 0.5);
        assertEquals("Wrapper", wrapper.getName());
        assertEquals(0.5, wrapper.getWeight(), 0.001);
        assertEquals(0, wrapper.getWeightNetto(), 0.001);
        assertEquals(0.5, wrapper.getWeightBrutto(), 0.001);
    }

    @Test
    public void testGoodsWrapperCopyConstructor() {
        GoodsWrapper original = new GoodsWrapper("Original", 1.0);
        GoodsWrapper copy = new GoodsWrapper(original);
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getWeight(), copy.getWeight(), 0.001);
    }
}
