package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class GroupDataTest {

    private Group group1;
    private Group group2;
    private Group emptyGroup;
    private Data data;

    @BeforeEach
    void setUp() {
        group1 = new Group(100, 1, 2, 3);
        group2 = new Group(200, 4, 5);
        emptyGroup = new Group(300);
        data = new Data("Test", group1, group2, emptyGroup);
    }

    @Test
    void groupConstructorValidates() {
        assertThrows(NullPointerException.class, () -> new Group(null, 1, 2));
        assertThrows(IllegalArgumentException.class, () -> new Group(-1, 1, 2));
        assertThrows(NullPointerException.class, () -> new Group(1, null));
        assertDoesNotThrow(() -> new Group(1));
    }

    @Test
    void groupGettersSetters() {
        Group g = new Group(10, 5, 6, 7);
        assertEquals(10, g.getId());
        assertArrayEquals(new Integer[]{5, 6, 7}, g.getData());
        assertEquals(3, g.size());

        g.setId(20);
        assertEquals(20, g.getId());
        g.setData(new Integer[]{8, 9});
        assertArrayEquals(new Integer[]{8, 9}, g.getData());
        assertEquals(2, g.size());

        assertThrows(NullPointerException.class, () -> g.setId(null));
        assertThrows(IllegalArgumentException.class, () -> g.setId(-5));
        assertThrows(NullPointerException.class, () -> g.setData(null));
    }

    @Test
    void dataConstructorValidates() {
        assertThrows(NullPointerException.class, () -> new Data(null, group1));
        assertThrows(NullPointerException.class, () -> new Data("Name", (Group[]) null));
        assertThrows(NullPointerException.class, () -> new Data("Name", group1, null));
        assertDoesNotThrow(() -> new Data("Name"));
        assertDoesNotThrow(() -> new Data("Name", new Group[0]));
    }

    @Test
    void dataGettersSetters() {
        Data d = new Data("Original", group1, group2);
        assertEquals("Original", d.getName());
        assertEquals(2, d.size());
        assertArrayEquals(new Group[]{group1, group2}, d.getData());

        d.setName("NewName");
        assertEquals("NewName", d.getName());

        Group g3 = new Group(300, 7, 8);
        d.setData(new Group[]{g3});
        assertArrayEquals(new Group[]{g3}, d.getData());
        assertEquals(1, d.size());

        assertThrows(NullPointerException.class, () -> d.setName(null));
        assertThrows(NullPointerException.class, () -> d.setData(null));
    }

    @Test
    void dataIteratorIteratesAllNumbers() {
        List<Integer> actual = StreamSupport.stream(data.spliterator(), false)
                .collect(Collectors.toList());
        assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5), actual);
    }

    @Test
    void dataIteratorWithEmptyGroups() {
        Data emptyData = new Data("Empty");
        Iterator<Integer> it = emptyData.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void dataIteratorWithOnlyEmptyGroups() {
        Data d = new Data("OnlyEmpty", new Group(1), new Group(2));
        Iterator<Integer> it = d.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    void dataDemoGetAll() {
        List<Integer> all = DataDemo.getAll(data);
        assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5), all);
    }

    @Test
    void dataDemoGetAllWithEmptyData() {
        Data empty = new Data("Empty");
        List<Integer> all = DataDemo.getAll(empty);
        assertIterableEquals(List.of(), all);
    }
}
