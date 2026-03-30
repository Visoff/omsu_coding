package ru.visoff;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {
    private Map<Human, List<String>> phoneBook;

    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    public void addPhone(Human human, String phone) {
        phoneBook.computeIfAbsent(human, k -> new ArrayList<>()).add(phone);
    }

    public void deletePhone(String phone) {
        Iterator<Map.Entry<Human, List<String>>> iterator = phoneBook.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Human, List<String>> entry = iterator.next();
            List<String> phones = entry.getValue();
            phones.remove(phone);
            if (phones.isEmpty()) {
                iterator.remove();
            }
        }
    }

    public List<String> getPhones(Human human) {
        List<String> phones = phoneBook.get(human);
        return phones == null ? new ArrayList<>() : new ArrayList<>(phones);
    }

    /**
     * Assumes that phone number is unique
     */
    public Human findHuman(String phone) {
        for (Map.Entry<Human, List<String>> entry : phoneBook.entrySet()) {
            if (entry.getValue().contains(phone)) {
                return entry.getKey(); // FIX: copy
            }
        }
        return null;
    }

    public Map<Human, List<String>> findRecordsBySurnameBeginning(String prefix) {
        return phoneBook.entrySet().stream()
                .filter(entry -> entry.getKey().getSurname().startsWith(prefix))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
    }
}
