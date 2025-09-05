package oop.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Task4 {
    private static final String SOURCE_PATH = "src/testCopy.txt";
    private static final String TARGET_PATH = "src/newFolder/test1.txt";
    public static void main(String[] args) throws IOException {
        Path sourcePath = Paths.get(SOURCE_PATH);
        Path targetPath = Paths.get(TARGET_PATH);

        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
