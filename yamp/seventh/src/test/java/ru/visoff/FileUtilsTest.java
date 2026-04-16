package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @TempDir
    Path tempDir;

    @Test
    void listFilesWithExtension() throws IOException {
        File dir = tempDir.toFile();
        Files.createFile(tempDir.resolve("a.txt"));
        Files.createFile(tempDir.resolve("b.TXT"));
        Files.createFile(tempDir.resolve("c.pdf"));
        Files.createDirectory(tempDir.resolve("subdir"));

        List<File> txtFiles = FileUtils.listFilesWithExtension(dir, "txt");
        assertEquals(2, txtFiles.size());
        assertTrue(txtFiles.stream().allMatch(f -> f.getName().toLowerCase().endsWith(".txt")));
    }

    @Test
    void listFilesWithExtension_nullArguments() {
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesWithExtension(null, "txt"));
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesWithExtension(new File("."), null));
    }

    @Test
    void listFilesAndDirsMatchingRegex() throws IOException {
        Files.createFile(tempDir.resolve("abc123.txt"));
        Files.createFile(tempDir.resolve("def456.log"));
        Files.createDirectory(tempDir.resolve("abc789_dir"));
        Files.createFile(tempDir.resolve("abc789_dir/abc999.txt"));

        List<File> result = FileUtils.listFilesAndDirsMatchingRegex(tempDir.toFile(), "abc.*");

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("abc123.txt")));
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("abc789_dir")));
        assertTrue(result.stream().anyMatch(f -> f.getName().equals("abc999.txt")));
        assertTrue(result.stream().allMatch(f -> f.getAbsolutePath().startsWith(tempDir.toAbsolutePath().toString())));
    }

    @Test
    void listFilesAndDirsMatchingRegex_nullArguments() {
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesAndDirsMatchingRegex(null, ".*"));
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesAndDirsMatchingRegex(new File("."), null));
    }
}
