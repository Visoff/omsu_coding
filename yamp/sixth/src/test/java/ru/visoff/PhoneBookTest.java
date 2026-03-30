package ru.visoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneBookTest {

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
    void addPhone() {
        phoneBook.addPhone(ivanov, "12345");
        phoneBook.addPhone(ivanov, "67890");
        List<String> phones = phoneBook.getPhones(ivanov);
        assertIterableEquals(List.of("12345", "67890"), phones);
    }

    @Test
    void deletePhone() {
        phoneBook.addPhone(ivanov, "12345");
        phoneBook.addPhone(ivanov, "67890");
        phoneBook.addPhone(petrov, "12345");

        phoneBook.deletePhone("12345");
        assertIterableEquals(List.of("67890"), phoneBook.getPhones(ivanov));
        assertTrue(phoneBook.getPhones(petrov).isEmpty());

        phoneBook.deletePhone("99999");
        assertIterableEquals(List.of("67890"), phoneBook.getPhones(ivanov));
    }

    @Test
    void getPhones() {
        assertTrue(phoneBook.getPhones(ivanov).isEmpty());

        phoneBook.addPhone(ivanov, "12345");
        assertIterableEquals(List.of("12345"), phoneBook.getPhones(ivanov));

        List<String> phones = phoneBook.getPhones(ivanov);
        phones.add("hack");
        assertIterableEquals(List.of("12345"), phoneBook.getPhones(ivanov));
    }

    @Test
    void findHuman() {
        phoneBook.addPhone(ivanov, "111");
        phoneBook.addPhone(petrov, "222");
        phoneBook.addPhone(sidorov, "333");

        assertEquals(ivanov, phoneBook.findHuman("111"));
        assertEquals(petrov, phoneBook.findHuman("222"));
        assertEquals(sidorov, phoneBook.findHuman("333"));
        assertNull(phoneBook.findHuman("999"));
    }

    @Test
    void findRecordsBySurnameBeginning() {
        phoneBook.addPhone(ivanov, "111");
        phoneBook.addPhone(ivanov, "112");
        phoneBook.addPhone(petrov, "222");
        phoneBook.addPhone(sidorov, "333");

        Map<Human, List<String>> expected = Map.of(ivanov, List.of("111", "112"));

        Map<Human, List<String>> actual = phoneBook.findRecordsBySurnameBeginning("I");
        assertEquals(expected, actual);
    }

    @Test
    void findRecordsBySurnameBeginning_noMatches() {
        phoneBook.addPhone(ivanov, "111");
        Map<Human, List<String>> result = phoneBook.findRecordsBySurnameBeginning("X");
        assertTrue(result.isEmpty());
    }

    @Test
    void independenceOfReturnedMap() {
        phoneBook.addPhone(ivanov, "111");
        Map<Human, List<String>> result = phoneBook.findRecordsBySurnameBeginning("I");
        List<String> list = result.get(ivanov);
        list.add("modified");
        assertIterableEquals(List.of("111"), phoneBook.getPhones(ivanov));
    }
}
