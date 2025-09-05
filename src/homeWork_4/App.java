package homeWork_4;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация работы с классом Телевизор ===\n");

        System.out.println("1. Создание телевизоров с помощью разных конструкторов:");

        Televison tv1 = new Televison();
        System.out.println("Телевизор 1 (по умолчанию):");
        tv1.showInfo();

        Televison tv2 = new Televison("Samsung", 55, "UE55AU7170U");
        System.out.println("\nТелевизор 2 (с параметрами):");
        tv2.showInfo();

        Televison tv3 = new Televison("LG", 65, "OLED65C1", true, 5, 75);
        System.out.println("\nТелевизор 3 (со всеми параметрами):");
        tv3.showInfo();

        System.out.println("\n2. Демонстрация работы методов:");
        tv1.turnOn();
        tv1.changeChannel(10);
        tv1.increaseVolume();
        tv1.increaseVolume();
        tv1.showInfo();

        System.out.println("\n3. Создание телевизора с параметрами с клавиатуры:");
        Scanner scanner = new Scanner(System.in);
        Televison tv4 = createTvFromKeyboard(scanner);
        tv4.showInfo();

        System.out.println("\n4. Создание телевизора со случайными параметрами:");
        Televison tv5 = new Televison();
        tv5.setRandomParameters();
        tv5.showInfo();

        System.out.println("\n5. Дополнительная демонстрация:");
        tv5.turnOn();
        tv5.changeChannel(25);
        for (int i = 0; i < 3; i++) {
            tv5.decreaseVolume();
        }
        tv5.showInfo();

        scanner.close();
    }

    public static Televison createTvFromKeyboard(Scanner scanner) {
        System.out.print("Введите марку телевизора: ");
        String brand = scanner.nextLine();

        System.out.print("Введите размер экрана (дюймы): ");
        int screenSize = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        System.out.print("Введите модель телевизора: ");
        String model = scanner.nextLine();

        return new Televison(brand, screenSize, model);
    }
}