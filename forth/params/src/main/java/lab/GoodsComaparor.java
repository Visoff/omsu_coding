package lab;

import java.util.Comparator;

import lab31.Goods;

public class GoodsComaparor implements Comparator<Goods> {
	@Override
	public int compare(Goods a1, Goods a2) {
        int res = a1.getName().compareTo(a2.getName());
        if (res == 0) {
            res = a1.getDescription().compareTo(a2.getDescription());
        }
        return res;
	}
}
