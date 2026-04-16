package ru.visoff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

public class IOUtils {

    public static void writeIntArray(OutputStream out, int[] array) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            for (int value : array) {
                dos.writeInt(value);
            }
        }
    }

    public static void readIntArray(InputStream in, int[] array) throws IOException {
        try (DataInputStream dis = new DataInputStream(in)) {
            for (int i = 0; i < array.length; i++) {
                array[i] = dis.readInt();
            }
        }
    }

    public static void writeIntArrayAsText(Writer out, int[] array) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(out)) {
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    bw.write(' ');
                }
                bw.write(Integer.toString(array[i]));
            }
        }
    }

    public static void readIntArrayFromText(Reader in, int[] array) throws IOException {
        try (BufferedReader br = new BufferedReader(in)) {
            String line = br.readLine();
            if (line == null) {
                return;
            }
            String[] parts = line.trim().split("\\s+");
            for (int i = 0; i < array.length && i < parts.length; i++) {
                array[i] = Integer.parseInt(parts[i]);
            }
        }
    }

    public static void readIntArrayFromPosition(RandomAccessFile raf, long position, int[] array) throws IOException {
        raf.seek(position);
        for (int i = 0; i < array.length; i++) {
            array[i] = raf.readInt();
        }
    }
}
