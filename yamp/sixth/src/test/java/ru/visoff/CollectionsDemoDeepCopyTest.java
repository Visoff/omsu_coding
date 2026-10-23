package ru.visoff;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for task 3 (deep-copy variant): the returned list must not contain
 * the excluded person and must contain independent copies of the elements,
 * so that mutating an input element does not affect the output.
 */
public class CollectionsDemoDeepCopyTest {

    private final Human ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30);
    private final Human petrov = new Human("Petrov", "Petr", "Petrovich", 25);

    @Test
    void copyWithoutIndependent_excludesGivenPerson() {
        Collection<Human> result = CollectionsDemo.copyWithoutIndependent(
                List.of(ivanov, petrov), ivanov);

        assertEquals(1, result.size());
        assertTrue(result.contains(petrov));
        assertFalse(result.contains(ivanov));
    }

    @Test
    void copyWithoutIndependent_removesAllEqualPeople() {
        Human twin = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        Collection<Human> result = CollectionsDemo.copyWithoutIndependent(
                List.of(ivanov, twin, petrov), ivanov);

        assertIterableEquals(List.of(petrov), result);
    }

    @Test
    void copyWithoutIndependent_elementsAreIndependentCopies() {
        List<Human> input = new java.util.ArrayList<>(List.of(ivanov, petrov));
        Collection<Human> result = CollectionsDemo.copyWithoutIndependent(input, new Human("Nobody", "No", "One", 1));

        assertEquals(2, result.size());

        input.get(0).setSurname("MUTATED");

        for (Human h : result) {
            assertNotEquals("MUTATED", h.getSurname());
        }
    }

    @Test
    void copyWithoutIndependent_mutatingOutputDoesNotAffectInput() {
        List<Human> input = new java.util.ArrayList<>(List.of(ivanov, petrov));
        Collection<Human> result = new java.util.ArrayList<>(
                CollectionsDemo.copyWithoutIndependent(input, new Human("Nobody", "No", "One", 1)));

        result.iterator().next().setAge(99);

        assertEquals(30, input.get(0).getAge());
    }

    @Test
    void copyWithoutIndependent_emptyInput() {
        assertTrue(CollectionsDemo.copyWithoutIndependent(List.of(), ivanov).isEmpty());
    }
}