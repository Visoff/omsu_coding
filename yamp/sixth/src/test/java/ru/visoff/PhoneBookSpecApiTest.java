package ru.visoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for task 12 spec-named methods: removePhone, findPersonByPhone,
 * findBySurnamePrefix (required API surface of the PhoneBook).
 */
public class PhoneBookSpecApiTest {

    private PhoneBook phoneBook;
    private Human ivanov;
    private Human petrov;
    private Human sidorov;

    @BeforeEach
    void setUp() {
        phoneBook = new PhoneBook();
        ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        petrov = new Human("Petrov", "Petr", "Petrovich", 25);
        sidorov = new Human("Sidorov", "Sidr", "Sidorovich", 30);
    }

    @Test
    void removePhone_deletesPhoneFromAllPeople() {
        phoneBook.addPhone(ivanov, "12345");
        phoneBook.addPhone(ivanov, "67890");
        phoneBook.addPhone(petrov, "12345");

        phoneBook.removePhone("12345");

        assertIterableEquals(List.of("67890"), phoneBook.getPhones(ivanov));
        assertTrue(phoneBook.getPhones(petrov).isEmpty());
    }

    @Test
    void findPersonByPhone_findsOwner() {
        phoneBook.addPhone(ivanov, "111");
        phoneBook.addPhone(sidorov, "333");

        assertEquals(ivanov, phoneBook.findPersonByPhone("111"));
        assertEquals(sidorov, phoneBook.findPersonByPhone("333"));
    }

    @Test
    void findPersonByPhone_returnsNullWhenAbsent() {
        assertNull(phoneBook.findPersonByPhone("missing"));
    }

    @Test
    void findBySurnamePrefix_filtersBySurnameBeginning() {
        phoneBook.addPhone(ivanov, "111");
        phoneBook.addPhone(ivanov, "112");
        phoneBook.addPhone(petrov, "222");
        phoneBook.addPhone(sidorov, "333");

        Map<Human, List<String>> expected = Map.of(ivanov, List.of("111", "112"));

        assertEquals(expected, phoneBook.findBySurnamePrefix("I"));
    }

    @Test
    void findBySurnamePrefix_noMatchesReturnsEmptyMap() {
        phoneBook.addPhone(ivanov, "111");

        assertTrue(phoneBook.findBySurnamePrefix("X").isEmpty());
    }
}