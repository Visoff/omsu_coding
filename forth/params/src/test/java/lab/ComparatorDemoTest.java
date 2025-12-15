package lab;

import lab31.Goods;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

public class ComparatorDemoTest {

    @Test
    void testSortGoodsByName() {
        Goods[] goods = {
            new Goods("Zebra", "Animal"),
            new Goods("Apple", "Fruit"),
            new Goods("Banana", "Fruit"),
            new Goods("Apple", "Tech Company")
        };
        
        ComparatorDemo.sortGoods(goods, new GoodsComaparor() {
            @Override
            public int compare(Goods a1, Goods a2) {
                return a1.getName().compareTo(a2.getName());
            }
        });
        
        assertEquals("Apple", goods[0].getName());
        assertEquals("Apple", goods[1].getName());
        assertEquals("Banana", goods[2].getName());
        assertEquals("Zebra", goods[3].getName());
    }

    @Test
    void testSortGoodsByNameAndDescription() {
        Goods[] goods = {
            new Goods("Apple", "Tech Company"),
            new Goods("Apple", "Fruit"),
            new Goods("Banana", "Yellow"),
            new Goods("Banana", "Green")
        };
        
        // Create comparator that compares by name then description
        GoodsComaparor comparator = new GoodsComaparor() {
            @Override
            public int compare(Goods a1, Goods a2) {
                int res = a1.getName().compareTo(a2.getName());
                if (res == 0) {
                    res = a1.getDescription().compareTo(a2.getDescription());
                }
                return res;
            }
        };
        
        ComparatorDemo.sortGoods(goods, comparator);
        
        // Verify sorted order
        assertEquals("Apple", goods[0].getName());
        assertEquals("Fruit", goods[0].getDescription());
        assertEquals("Apple", goods[1].getName());
        assertEquals("Tech Company", goods[1].getDescription());
        assertEquals("Banana", goods[2].getName());
        assertEquals("Green", goods[2].getDescription());
        assertEquals("Banana", goods[3].getName());
        assertEquals("Yellow", goods[3].getDescription());
    }

    @Test
    void testSortEmptyArray() {
        Goods[] goods = new Goods[0];
        assertDoesNotThrow(() -> {
            ComparatorDemo.sortGoods(goods, new GoodsComaparor());
        });
    }

    @Test
    void testSortSingleElement() {
        Goods[] goods = {new Goods("Single", "Item")};
        Goods[] original = goods.clone();
        
        ComparatorDemo.sortGoods(goods, new GoodsComaparor());
        
        assertArrayEquals(original, goods);
    }

    @Test
    void testGoodsComaparorImplementsComparable() {
        GoodsComaparor comparator = new GoodsComaparor();
        assertTrue(comparator instanceof Comparator<?>);
    }

    @Test
    void testCaseSensitiveSorting() {
        Goods[] goods = {
            new Goods("apple", "fruit"),
            new Goods("Apple", "Fruit"),
            new Goods("banana", "fruit")
        };
        
        ComparatorDemo.sortGoods(goods, new GoodsComaparor() {
            @Override
            public int compare(Goods a1, Goods a2) {
                return a1.getName().compareTo(a2.getName());
            }
        });
        
        assertEquals("Apple", goods[0].getName());
        assertEquals("apple", goods[1].getName());
        assertEquals("banana", goods[2].getName());
    }
}
