package ru.visoff;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        FilenameFilter filter = (dir, name) -> name.toLowerCase().endsWith(ext.toLowerCase());

        File[] files = directory.listFiles(filter);
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
        Pattern pattern = Pattern.compile(regex);
        List<File> result = new ArrayList<>();
        collectMatching(root, pattern, result);
        return result;
    }

    private static void collectMatching(File file, Pattern pattern, List<File> result) {
        if (file == null || !file.exists()) {
            return;
        }
        if (pattern.matcher(file.getName()).matches()) {
            result.add(file.getAbsoluteFile());
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    collectMatching(child, pattern, result);
                }
            }
        }
    }
}
