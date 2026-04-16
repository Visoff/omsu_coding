package ru.visoff;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HouseJsonSerializerTest {

    private House createTestHouse() {
        Person elder = new Person("Ivanov", "Ivan", "Ivanovich", LocalDate.of(1970, 5, 10));
        Person owner1 = new Person("Petrov", "Petr", "Petrovich", LocalDate.of(1980, 3, 15));
        Flat flat1 = new Flat(1, 40.5, List.of(owner1));
        return new House("12345", "Omsk", elder, List.of(flat1));
    }

    @Test
    void toJsonAndFromJson() throws IOException {
        House original = createTestHouse();
        String json = HouseJsonSerializer.toJson(original);
        assertNotNull(json);
        assertTrue(json.contains("cadastralNumber"));
        assertTrue(json.contains("12345"));

        House deserialized = HouseJsonSerializer.fromJson(json);
        assertEquals(original, deserialized);
    }
}
