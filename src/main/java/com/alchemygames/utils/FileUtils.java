package com.alchemygames.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    private FileUtils() {

    }

    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public static String readFileFromClassPath(String filePath) {
        String str;

        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(filePath);
        try {
            assert resource != null;
            str = new String(Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
