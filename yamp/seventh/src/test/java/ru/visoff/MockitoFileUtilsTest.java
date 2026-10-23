package ru.visoff;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MockitoFileUtilsTest {

    @Test
    void listFilesWithExtension_returnsOnlyMatchingFilesThroughPassedFilter() {
        File dir = mock(File.class);
        File txt = mock(File.class);
        File pdf = mock(File.class);

        when(dir.isDirectory()).thenReturn(true);
        when(txt.getName()).thenReturn("report.txt");
        when(txt.isFile()).thenReturn(true);
        when(pdf.getName()).thenReturn("report.pdf");
        when(pdf.isFile()).thenReturn(true);

        when(dir.listFiles(any(FilenameFilter.class))).thenAnswer(invocation -> {
            FilenameFilter filter = invocation.getArgument(0);
            return Arrays.stream(new File[] { txt, pdf })
                    .filter(f -> filter.accept(dir, f.getName()))
                    .toArray(File[]::new);
        });

        List<File> result = FileUtils.listFilesWithExtension(dir, "txt");

        assertEquals(1, result.size());
        assertEquals("report.txt", result.get(0).getName());
        verify(dir).listFiles(any(FilenameFilter.class));
    }

    @Test
    void listFilesWithExtension_buildsCaseInsensitiveFilter() {
        File dir = mock(File.class);
        when(dir.isDirectory()).thenReturn(true);
        when(dir.listFiles(any(FilenameFilter.class))).thenReturn(new File[0]);

        FileUtils.listFilesWithExtension(dir, "txt");

        ArgumentCaptor<FilenameFilter> captor = ArgumentCaptor.forClass(FilenameFilter.class);
        verify(dir).listFiles(captor.capture());
        FilenameFilter filter = captor.getValue();

        assertTrue(filter.accept(dir, "a.TXT"));
        assertTrue(filter.accept(dir, "file.txt"));
        assertFalse(filter.accept(dir, "a.pdf"));
        assertFalse(filter.accept(dir, "noextension"));
    }

    @Test
    void listFilesWithExtension_throwsForNonDirectory() {
        File notDir = mock(File.class);
        when(notDir.isDirectory()).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> FileUtils.listFilesWithExtension(notDir, "txt"));
    }

    @Test
    void listFilesWithExtension_throwsForNullArguments() {
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesWithExtension(null, "txt"));

        File dir = mock(File.class);
        when(dir.isDirectory()).thenReturn(true);
        assertThrows(NullPointerException.class, () -> FileUtils.listFilesWithExtension(dir, null));
    }

    @Test
    void listFilesAndDirsMatchingRegex_recursivelyCollectsMatches() {
        File root = mock(File.class);
        File notes = mock(File.class);
        File sub = mock(File.class);

        when(root.exists()).thenReturn(true);
        when(root.getName()).thenReturn("root");
        when(root.isDirectory()).thenReturn(true);
        when(root.listFiles()).thenReturn(new File[] { notes, sub });

        when(notes.exists()).thenReturn(true);
        when(notes.getName()).thenReturn("notes.txt");
        when(notes.getAbsoluteFile()).thenReturn(notes);

        when(sub.exists()).thenReturn(true);
        when(sub.getName()).thenReturn("sub");
        when(sub.isDirectory()).thenReturn(true);
        when(sub.getAbsoluteFile()).thenReturn(sub);
        when(sub.listFiles()).thenReturn(new File[0]);

        List<File> result = FileUtils.listFilesAndDirsMatchingRegex(root, ".*\\.txt$");

        assertEquals(1, result.size());
        assertEquals("notes.txt", result.get(0).getName());
        verify(root).listFiles();
        verify(sub).listFiles();
    }

    @Test
    void listFilesAndDirsMatchingRegex_stopsAtNonexistentFiles() {
        File root = mock(File.class);
        File ghost = mock(File.class);

        when(root.exists()).thenReturn(true);
        when(root.getName()).thenReturn("root");
        when(root.isDirectory()).thenReturn(true);
        when(root.listFiles()).thenReturn(new File[] { ghost });

        when(ghost.exists()).thenReturn(false);
        when(ghost.getName()).thenReturn("ghost.txt");

        List<File> result = FileUtils.listFilesAndDirsMatchingRegex(root, ".*\\.txt$");

        assertTrue(result.isEmpty());
        verify(ghost, never()).getAbsoluteFile();
        verify(ghost, never()).listFiles();
    }
}
