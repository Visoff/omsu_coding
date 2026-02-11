package ru.visoff;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionsDemo {

    public static long countStringsStartingWith(Collection<String> strs, char ch) {
        return strs.stream()
                .filter(s -> s.startsWith(String.valueOf(ch)))
                .count();
    }

    public static Collection<Human> selectNamesakes(Collection<Human> humans, Human human) {
        return humans.stream()
                .filter(h -> h.getSurname().equals(human.getSurname()))
                .collect(Collectors.toList());
    }

    public static Collection<Human> copyWithout(Collection<Human> humans, Human human) {
        return humans.stream()
                .filter(h -> !h.equals(human))
                .collect(Collectors.toList());
    }

    private static boolean areSetsIntersecting(Set<Integer> set1, Set<Integer> set2) {
        return set1.stream().anyMatch(set2::contains);
    }

    public static Collection<Set<Integer>> selectNotIntersected(Collection<Set<Integer>> sets, Set<Integer> set) {
        return sets.stream()
                .filter(s -> !areSetsIntersecting(s, set))
                .collect(Collectors.toList());
    }

    public static <E extends Human> Set<E> selectEldest(Collection<E> humans) {
        if (humans.isEmpty()) {
            return Collections.emptySet();
        }
        E eldest = humans.stream()
                .max(Comparator.comparingInt(Human::getAge))
                .orElseThrow(NoSuchElementException::new);

        Integer age = eldest.getAge();
        return humans.stream()
                .filter(h -> h.getAge().equals(age))
                .collect(Collectors.toSet());
    }

    public static <E extends Human> List<E> selectOrderedByFIO(Collection<E> humans) {
        TreeSet<E> treeSet = new TreeSet<>((h1, h2) -> {
            int res = h1.getSurname().compareTo(h2.getSurname());
            if (res == 0)
                res = h1.getName().compareTo(h2.getName());
            if (res == 0)
                res = h1.getPatronymic().compareTo(h2.getPatronymic());
            return res;
        });
        treeSet.addAll(humans);
        return new ArrayList<>(treeSet);
    }

    public static Set<Human> mapById(Map<Integer, Human> humans, Set<Integer> ids) {
        return ids.stream()
                .map(humans::get)
                .collect(Collectors.toSet());
    }

    public static Collection<Integer> selectAdults(Map<Integer, Human> humans) {
        return humans.keySet().stream()
                .filter(id -> humans.get(id).getAge() >= 18)
                .collect(Collectors.toList());
    }

    public static Map<Integer, Integer> mapAges(Map<Integer, Human> humans) {
        return humans.keySet().stream()
                .collect(Collectors.toMap(id -> id, id -> humans.get(id).getAge()));
    }

    public static Map<Integer, List<Human>> mapByAge(Set<Human> humans) {
        return humans.stream()
                .collect(Collectors.groupingBy(Human::getAge, Collectors.toList()));
    }

    public static Map<Integer, Map<Character, List<Human>>> mapByAgeAndInitialSurnameLetter(Set<Human> humans) {
        Function<Human, Character> firstSurnameLetter = h -> h.getSurname().charAt(0);

        Comparator<Human> fioComparator = (h1, h2) -> {
            int res = h1.getSurname().compareTo(h2.getSurname());
            if (res == 0)
                res = h1.getName().compareTo(h2.getName());
            if (res == 0)
                res = h1.getPatronymic().compareTo(h2.getPatronymic());
            return res;
        };

        // descending order
        Comparator<Human> descending = fioComparator.reversed();

        return mapByAge(humans).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(descending)
                                .collect(Collectors.groupingBy(firstSurnameLetter, Collectors.toList()))));
    }
}
