package lab;

import java.util.Arrays;

import lab31.Goods;

public class ComparatorDemo {
    public static void sortGoods(Goods[] goods, GoodsComaparor comparator) {
        Arrays.sort(goods, comparator);
    }
}
