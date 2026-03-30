package ru.visoff;

import java.util.ArrayList;
import java.util.List;

public class DataDemo {
    public static List<Integer> getAll(Data data) {
        List<Integer> res = new ArrayList<>();
        for (Integer el : data) {
            res.add(el);
        }
        return res;
    }
}
