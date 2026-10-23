package ru.visoff;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class MockitoIOUtilsTest {

    @Test
    void writeIntArray_writesFourBytesPerInt() throws IOException {
        OutputStream out = mock(OutputStream.class);
        List<byte[]> written = new ArrayList<>();
        doAnswer(invocation -> {
            byte[] src = invocation.getArgument(0);
            int off = invocation.getArgument(1);
            int len = invocation.getArgument(2);
            written.add(Arrays.copyOfRange(src, off, off + len));
            return null;
        }).when(out).write(any(byte[].class), anyInt(), anyInt());

        IOUtils.writeIntArray(out, new int[] { 1, 2 });

        assertEquals(2, written.size());
        assertArrayEquals(new byte[] { 0, 0, 0, 1 }, written.get(0));
        assertArrayEquals(new byte[] { 0, 0, 0, 2 }, written.get(1));
        verify(out).close();
    }

    @Test
    void readIntArray_readsFourBytesPerInt() throws IOException {
        InputStream in = mock(InputStream.class);

        final byte[] content = new byte[] { 0, 0, 0, 5, 0, 0, 0, (byte) 0xFB };
        final int[] pos = { 0 };
        when(in.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            byte[] cbuf = invocation.getArgument(0);
            int off = invocation.getArgument(1);
            int len = invocation.getArgument(2);
            int remaining = content.length - pos[0];
            if (remaining <= 0) {
                return -1;
            }
            int n = Math.min(len, remaining);
            System.arraycopy(content, pos[0], cbuf, off, n);
            pos[0] += n;
            return n;
        });

        int[] array = new int[2];
        IOUtils.readIntArray(in, array);

        assertArrayEquals(new int[] { 5, 0xFB }, array);
        verify(in, atLeast(1)).read(any(byte[].class), anyInt(), anyInt());
    }

    @Test
    void writeIntArrayAsText_writesSpaceSeparatedValues() throws IOException {
        Writer writer = mock(Writer.class);

        IOUtils.writeIntArrayAsText(writer, new int[] { 1, 22, 333 });

        ArgumentCaptor<char[]> charsCaptor = ArgumentCaptor.forClass(char[].class);
        ArgumentCaptor<Integer> offCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> lenCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(writer).write(charsCaptor.capture(), offCaptor.capture(), lenCaptor.capture());

        String text = new String(charsCaptor.getValue(), offCaptor.getValue(), lenCaptor.getValue());
        assertEquals("1 22 333", text);
    }

    @Test
    void readIntArrayFromText_parsesSpaceSeparatedTokens() throws IOException {
        Reader reader = mock(Reader.class);
        final char[] content = "11 222 3".toCharArray();
        final int[] pos = { 0 };
        when(reader.read(any(char[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            char[] cbuf = invocation.getArgument(0);
            int off = invocation.getArgument(1);
            int len = invocation.getArgument(2);
            int remaining = content.length - pos[0];
            if (remaining <= 0) {
                return -1;
            }
            int n = Math.min(len, remaining);
            System.arraycopy(content, pos[0], cbuf, off, n);
            pos[0] += n;
            return n;
        });

        int[] array = new int[3];
        IOUtils.readIntArrayFromText(reader, array);

        assertArrayEquals(new int[] { 11, 222, 3 }, array);
    }

    @Test
    void readIntArrayFromPosition_seeksAndReadsSequentially() throws IOException {
        RandomAccessFile raf = mock(RandomAccessFile.class);
        when(raf.readInt()).thenReturn(7, 8);

        int[] array = new int[2];
        IOUtils.readIntArrayFromPosition(raf, 42, array);

        verify(raf).seek(42);
        assertArrayEquals(new int[] { 7, 8 }, array);
    }
}
