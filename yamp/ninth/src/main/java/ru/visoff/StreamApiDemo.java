package ru.visoff;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamApiDemo extends LambdaDemo {
    public static final Function<List<Object>, List<Object>> REMOVE_NULLS = list -> list.stream()
            .filter(Objects::nonNull).collect(Collectors.toList());

    public static final Function<Set<Integer>, Long> COUNT_POSITIVES = set -> set.stream().filter(n -> n > 0).count();

    public static final Function<List<Object>, List<Object>> LAST_THREE_ELEMENTS = list -> list.stream()
            .skip(Math.max(0, list.size() - 3)).collect(Collectors.toList());

    public static final Function<List<Integer>, Integer> FIRST_EVEN_NUMBER = list -> list.stream()
            .filter(n -> n % 2 == 0).findFirst().orElse(null);

    public static final Function<int[], List<Integer>> DISTINCT_SQUARES = arr -> Arrays.stream(arr).boxed().distinct()
            .map(n -> n * n).collect(Collectors.toList());

    public static final Function<List<String>, List<String>> NON_EMPTY_STRINGS_SORTED = list -> list.stream()
            .filter(s -> !s.isEmpty()).sorted().collect(Collectors.toList());

    public static final Function<Set<String>, List<String>> STRINGS_SORTED_DESCENDING = set -> set.stream()
            .sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    public static final Function<Set<Integer>, Integer> SUM_OF_SQUARES = set -> set.stream().mapToInt(n -> n * n).sum();

    public static final Function<Collection<Human>, Integer> MAX_AGE = humans -> humans.stream().mapToInt(Human::getAge)
            .max().orElse(0);

    public static final Function<Collection<Human>, List<Human>> SORT_BY_SEX_THEN_AGE = humans -> humans.stream()
            .sorted(Comparator.comparing(Human::getSex).thenComparingInt(Human::getAge))
            .collect(Collectors.toList());
}
