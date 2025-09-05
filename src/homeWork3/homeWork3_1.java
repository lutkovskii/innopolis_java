package homeWork3;
import java.sql.SQLOutput;
import java.util.Scanner;

public class homeWork3_1 {
    public static void main(String[] args) {
        //  Задача 1. Составить программу вывода на экран в одну строку сообщения
//«Привет, имя_пользователя», где «имя_пользователя» - это введёное в консоль
//имя, для выполнения данного задания нужно использовать класс Scanner.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя: ");
        String a = scanner.nextLine();
        System.out.println("Привет, " + a);
    }
}

