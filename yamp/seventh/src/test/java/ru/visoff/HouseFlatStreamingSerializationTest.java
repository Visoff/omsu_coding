package ru.visoff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HouseFlatStreamingSerializationTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper()
                .registerModule(new JacksonStreamingModule())
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void houseIsSerializedWithStreamingSerializers() throws IOException {
        Person elder = new Person("Sidorov", "Sidor", "Sidorovich", LocalDate.of(1965, 3, 3));
        Flat flat = new Flat(10, 55.5, List.of(elder));
        House houseWithFlats = new House("ABC-123", "Moscow", elder, List.of(flat));

        String json = mapper.writeValueAsString(houseWithFlats);

        assertTrue(json.contains("\"cadastralNumber\""));
        assertTrue(json.contains("\"address\""));
        assertTrue(json.contains("\"flats\""));
        assertTrue(json.contains("\"number\""));
        assertTrue(json.contains("\"owners\""));

        assertTrue(json.contains("\"fullName\""));
        assertTrue(json.contains("Sidorov Sidor Sidorovich"));
    }

    @Test
    void houseRoundTripsExactlyThroughStreamingSerializers() throws IOException {
        Person elder = new Person("Sidorov", "Sidor", "Sidorovich", LocalDate.of(1965, 3, 3));
        Person owner1 = new Person("Kuznetsov", "Kuzma", "Kuzmich", LocalDate.of(1995, 7, 7));
        Person owner2 = new Person("Kuznetsova", "Kira", "Kuzminichna", LocalDate.of(1998, 9, 9));
        Flat flat1 = new Flat(10, 55.5, List.of(owner1));
        Flat flat2 = new Flat(11, 62.25, List.of(owner1, owner2));
        House original = new House("ABC-123", "Moscow", elder, List.of(flat1, flat2));

        String json = mapper.writeValueAsString(original);
        House restored = mapper.readValue(json, House.class);

        assertEquals(original, restored);
    }

    @Test
    void flatRoundTripsExactlyThroughStreamingSerializers() throws IOException {
        Person owner = new Person("Petrov", "Petr", "Petrovich", LocalDate.of(1990, 5, 15));
        Flat original = new Flat(7, 48.75, List.of(owner));

        String json = mapper.writeValueAsString(original);
        Flat restored = mapper.readValue(json, Flat.class);

        assertEquals(original, restored);
    }
}
