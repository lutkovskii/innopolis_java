package oop.nio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
/*
Создать папку и подпапку в src
 */
public class Task2 {
    private static final String NEW_FOLDER = "src/newFolder";
    private static final String NEW_SUB_FOLDER = NEW_FOLDER + "/newSubFolder";

    public static void main(String[] args) {
        Path path1 = Paths.get(NEW_FOLDER);
        Path path2 = Paths.get(NEW_SUB_FOLDER);
        try {
//            Files.createDirectory(path1);
//            Files.createDirectory(path2);
            Files.createDirectories(path2);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
