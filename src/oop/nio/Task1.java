package oop.nio;
/*
Создать файл в /src с названием test.txt
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task1 {
    public static void main(String[] args) {
        Path path = Paths.get("src/test.txt");
        try{
            Path createdFile = Files.createFile(path);
            System.out.println("File created successfully:" + createdFile);
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
