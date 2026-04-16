package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HouseSerializerTest {

    @TempDir
    Path tempDir;

    private House createTestHouse() {
        Person elder = new Person("Ivanov", "Ivan", "Ivanovich", LocalDate.of(1970, 5, 10));
        Person owner1 = new Person("Petrov", "Petr", "Petrovich", LocalDate.of(1980, 3, 15));
        Person owner2 = new Person("Sidorova", "Olga", "Mikhailovna", LocalDate.of(1990, 7, 20));
        Flat flat1 = new Flat(1, 40.5, List.of(owner1));
        Flat flat2 = new Flat(2, 65.0, List.of(owner2));
        return new House("12345", "Omsk, Mira 321", elder, List.of(flat1, flat2));
    }

    @Test
    void serializeAndDeserialize() throws IOException, ClassNotFoundException {
        House original = createTestHouse();
        Path file = tempDir.resolve("house.ser");

        try (OutputStream out = new FileOutputStream(file.toFile())) {
            HouseSerializer.serialize(original, out);
        }
        House deserialized;
        try (InputStream in = new FileInputStream(file.toFile())) {
            deserialized = HouseSerializer.deserialize(in);
        }
        assertEquals(original, deserialized);
    }
}
