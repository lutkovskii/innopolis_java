package oop.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
копировать файл
 */
public class Task3 {
    public static void main(String[] args) throws IOException {
        Path sourceFile = Paths.get("src/test.txt");
        Path targetFile = Paths.get("src/testCopy.txt");

        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
    }
}
