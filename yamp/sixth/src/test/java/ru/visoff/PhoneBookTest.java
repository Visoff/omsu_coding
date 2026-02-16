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
        assertEquals(2, phones.size());
        assertTrue(phones.contains("12345"));
        assertTrue(phones.contains("67890"));
    }

    @Test
    void deletePhone() {
        phoneBook.addPhone(ivanov, "12345");
        phoneBook.addPhone(ivanov, "67890");
        phoneBook.addPhone(petrov, "12345");

        phoneBook.deletePhone("12345");
        assertFalse(phoneBook.getPhones(ivanov).contains("12345"));
        assertTrue(phoneBook.getPhones(ivanov).contains("67890"));
        assertTrue(phoneBook.getPhones(petrov).isEmpty());

        phoneBook.deletePhone("99999");
        assertEquals(1, phoneBook.getPhones(ivanov).size());
    }

    @Test
    void getPhones() {
        assertTrue(phoneBook.getPhones(ivanov).isEmpty());

        phoneBook.addPhone(ivanov, "12345");
        List<String> phones = phoneBook.getPhones(ivanov);
        assertEquals(1, phones.size());
        assertEquals("12345", phones.get(0));

        phones.add("hack");
        assertEquals(1, phoneBook.getPhones(ivanov).size());
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

        Map<Human, List<String>> result = phoneBook.findRecordsBySurnameBeginning("Iva");
        assertEquals(1, result.size());
        assertTrue(result.containsKey(ivanov));
        assertEquals(2, result.get(ivanov).size());

        result = phoneBook.findRecordsBySurnameBeginning("S");
        assertEquals(1, result.size());
        assertTrue(result.containsKey(sidorov));

        result = phoneBook.findRecordsBySurnameBeginning("X");
        assertTrue(result.isEmpty());
    }

    @Test
    void independenceOfReturnedMap() {
        phoneBook.addPhone(ivanov, "111");
        Map<Human, List<String>> result = phoneBook.findRecordsBySurnameBeginning("I");
        List<String> list = result.get(ivanov);
        list.add("modified");
        assertEquals(1, phoneBook.getPhones(ivanov).size());
        assertFalse(phoneBook.getPhones(ivanov).contains("modified"));
    }
}
