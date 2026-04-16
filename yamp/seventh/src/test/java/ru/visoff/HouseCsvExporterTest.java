package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HouseCsvExporterTest {

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
    void exportToCsv_createsFile() throws IOException {
        House house = createTestHouse();
        Path originalDir = Path.of(".");
        Path expectedFile = originalDir.resolve("house_12345.csv");

        if (Files.exists(expectedFile)) {
            Files.delete(expectedFile);
        }

        HouseCsvExporter.exportToCsv(house);

        assertTrue(Files.exists(expectedFile));
        String content = Files.readString(expectedFile);
        assertTrue(content.contains("Кадастровый номер:;12345"));
        assertTrue(content.contains("Адрес:;Omsk, Mira 321"));
        assertTrue(content.contains("Старший по дому:;Ivanov Ivan Ivanovich"));
        assertTrue(content.contains("1;40.5;Petrov P.P."));
        assertTrue(content.contains("2;65.0;Sidorova O.M."));

        Files.delete(expectedFile);
    }

    @Test
    void exportToCsv_nullHouse() {
        assertThrows(NullPointerException.class, () -> HouseCsvExporter.exportToCsv(null));
    }
}
