package ru.visoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JacksonStreamingModuleTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JacksonStreamingModule());
        mapper.findAndRegisterModules();
    }

    @Test
    void personSerializationUsesFullName() throws IOException {
        Person person = new Person("Ivanov", "Ivan", "Ivanovich", LocalDate.of(1990, 1, 1));
        String json = mapper.writeValueAsString(person);
        assertTrue(json.contains("fullName"));
        assertTrue(json.contains("Ivanov Ivan Ivanovich"));
        assertFalse(json.contains("surname"));
    }

    @Test
    void personDeserializationFromFullName() throws IOException {
        String json = "{\"fullName\":\"Petrov Petr Petrovich\",\"birthDate\":\"1985-05-15\"}";
        Person person = mapper.readValue(json, Person.class);
        assertEquals("Petrov", person.getSurname());
        assertEquals("Petr", person.getName());
        assertEquals("Petrovich", person.getPatronymic());
        assertEquals(LocalDate.of(1985, 5, 15), person.getBirthDate());
    }

    @Test
    void roundTripWithHouse() throws IOException {
        Person elder = new Person("Sidorov", "Sidor", "Sidorovich", LocalDate.of(1965, 3, 3));
        Person owner = new Person("Kuznetsov", "Kuzma", "Kuzmich", LocalDate.of(1995, 7, 7));
        Flat flat = new Flat(10, 55.5, List.of(owner));
        House original = new House("ABC-123", "Moscow", elder, List.of(flat));

        String json = mapper.writeValueAsString(original);
        House deserialized = mapper.readValue(json, House.class);

        assertEquals(original.getCadastralNumber(), deserialized.getCadastralNumber());
        assertEquals(original.getAddress(), deserialized.getAddress());
        assertEquals(original.getElder().getSurname(), deserialized.getElder().getSurname());
        assertEquals(original.getFlats().get(0).getNumber(), deserialized.getFlats().get(0).getNumber());
    }
}
