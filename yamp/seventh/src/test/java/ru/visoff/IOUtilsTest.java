package ru.visoff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilsTest {

    @TempDir
    Path tempDir;

    @Test
    void writeAndReadIntArrayBinary() throws IOException {
        int[] original = {10, 20, 30, 40};
        int[] result = new int[4];
        Path file = tempDir.resolve("binary.dat");

        try (OutputStream out = new FileOutputStream(file.toFile())) {
            IOUtils.writeIntArray(out, original);
        }
        try (InputStream in = new FileInputStream(file.toFile())) {
            IOUtils.readIntArray(in, result);
        }
        assertArrayEquals(original, result);
    }

    @Test
    void writeAndReadIntArrayText() throws IOException {
        int[] original = {5, 15, 25};
        int[] result = new int[3];
        Path file = tempDir.resolve("text.txt");

        try (Writer w = new FileWriter(file.toFile())) {
            IOUtils.writeIntArrayAsText(w, original);
        }
        try (Reader r = new FileReader(file.toFile())) {
            IOUtils.readIntArrayFromText(r, result);
        }
        assertArrayEquals(original, result);
    }

    @Test
    void readIntArrayFromPosition() throws IOException {
        int[] all = {100, 200, 300, 400, 500};
        int[] target = new int[2];
        Path file = tempDir.resolve("random.dat");

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.toFile()))) {
            for (int v : all) dos.writeInt(v);
        }
        try (RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r")) {
            IOUtils.readIntArrayFromPosition(raf, 8, target);
        }
        assertArrayEquals(new int[]{300, 400}, target);
    }
}
