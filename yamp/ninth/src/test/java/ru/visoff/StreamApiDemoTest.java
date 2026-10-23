package ru.visoff;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StreamApiDemoTest {

    private final Human ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30, Sex.MALE);
    private final Human petrova = new Human("Petrova", "Petra", "Petrovna", 25, Sex.FEMALE);
    private final Human sidorov = new Human("Sidorov", "Sidr", "Sidorovich", 40, Sex.MALE);

    @Test
    void removeNulls() {
        java.util.List<Object> input = java.util.Arrays.asList("a", null, "b", null, "c");
        assertEquals(List.of("a", "b", "c"), StreamApiDemo.REMOVE_NULLS.apply(input));
    }

    @Test
    void countPositives() {
        assertEquals(3, StreamApiDemo.COUNT_POSITIVES.apply(Set.of(-5, 0, 1, 2, 3)));
        assertEquals(0, StreamApiDemo.COUNT_POSITIVES.apply(Set.of(-5, -1)));
    }

    @Test
    void lastThreeElements() {
        assertEquals(List.of(3, 4, 5), StreamApiDemo.LAST_THREE_ELEMENTS.apply(List.of(1, 2, 3, 4, 5)));
        assertEquals(List.of(1, 2), StreamApiDemo.LAST_THREE_ELEMENTS.apply(List.of(1, 2)));
    }

    @Test
    void firstEvenNumber() {
        assertEquals(2, StreamApiDemo.FIRST_EVEN_NUMBER.apply(List.of(1, 2, 3, 4)));
        assertNull(StreamApiDemo.FIRST_EVEN_NUMBER.apply(List.of(1, 3, 5)));
    }

    @Test
    void distinctSquares() {
        assertEquals(List.of(1, 4, 1, 9), StreamApiDemo.DISTINCT_SQUARES.apply(new int[] { 1, 2, -1, 3, 2 }));
    }

    @Test
    void nonEmptyStringsSorted() {
        assertEquals(List.of("apple", "banana", "cherry"),
                StreamApiDemo.NON_EMPTY_STRINGS_SORTED.apply(List.of("", "banana", "apple", "cherry", "")));
    }

    @Test
    void stringsSortedDescending() {
        assertEquals(List.of("c", "b", "a"),
                StreamApiDemo.STRINGS_SORTED_DESCENDING.apply(Set.of("a", "b", "c")));
    }

    @Test
    void sumOfSquares() {
        assertEquals(14, StreamApiDemo.SUM_OF_SQUARES.apply(Set.of(1, 2, 3)));
        assertEquals(0, StreamApiDemo.SUM_OF_SQUARES.apply(Set.of()));
    }

    @Test
    void maxAge() {
        assertEquals(40, StreamApiDemo.MAX_AGE.apply(List.of(ivanov, petrova, sidorov)));
        assertEquals(0, StreamApiDemo.MAX_AGE.apply(List.of()));
    }

    @Test
    void sortBySexThenAge() {
        Human olderFemale = new Human("Z", "Z", "Z", 50, Sex.FEMALE);
        List<Human> sorted = StreamApiDemo.SORT_BY_SEX_THEN_AGE.apply(
                List.of(petrova, sidorov, olderFemale, ivanov));

        assertEquals(List.of(ivanov, sidorov, petrova, olderFemale), sorted);
    }
}
