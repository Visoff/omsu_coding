package ru.visoff;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DataDemo {
    public static List<Integer> getAll(Data data) {
        return StreamSupport.stream(data.spliterator(), false)
                .collect(Collectors.toList());
    }
}
