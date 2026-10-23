package ru.visoff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<File> listFilesWithExtension(File directory, String extension) {
        if (directory == null) {
            throw new NullPointerException("Directory can't be null");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + directory.getAbsolutePath());
        }
        if (extension == null) {
            throw new NullPointerException("Extension can't be null");
        }
        String ext = extension.startsWith(".") ? extension : "." + extension;

        File[] files = directory.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(ext.toLowerCase()));
        List<File> result = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    result.add(f);
                }
            }
        }
        return result;
    }

    public static List<File> listFilesAndDirsMatchingRegex(File root, String regex) {
        if (root == null) {
            throw new NullPointerException("Root directory can't be null");
        }
        if (regex == null) {
            throw new NullPointerException("Regex can't be null");
        }
        List<File> result = new ArrayList<>();
        collectMatching(root, regex, result);
        return result;
    }

    private static void collectMatching(File file, String regex, List<File> result) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.getName().matches(regex)) {
            result.add(file.getAbsoluteFile());
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    collectMatching(child, regex, result);
                }
            }
        }
    }
}
