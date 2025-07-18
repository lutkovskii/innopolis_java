package homeWork3;
import java.util.Random;

public class homeWork3_2 {
    public static void main(String[] args) {
        // Генерируем случайные выборы для игроков
        Random random = new Random();

        int vasyaChoice = random.nextInt(3); // Выбор числа между 0 и 2 включительно
        int petyaChoice = random.nextInt(3);

        System.out.println("Васин выбор: " + choiceToString(vasyaChoice));
        System.out.println("Петин выбор: " + choiceToString(petyaChoice));

        determineWinner(vasyaChoice, petyaChoice);
    }

    private static String choiceToString(int choice) {
        switch(choice) {
            case 0: return "камень";
            case 1: return "ножницы";
            case 2: return "бумага";
            default: throw new IllegalArgumentException("Недопустимый выбор фигуры");
        }
    }

    private static void determineWinner(int vasyaChoice, int petyaChoice) {
        if (vasyaChoice == petyaChoice) {
            System.out.println("Ничья!");
        } else if ((vasyaChoice == 0 && petyaChoice == 1) || // камень против ножниц
                (vasyaChoice == 1 && petyaChoice == 2) || // ножницы против бумаги
                (vasyaChoice == 2 && petyaChoice == 0)) {  // бумага против камня
            System.out.println("Выиграл Вася!");
        } else {
            System.out.println("Выиграл Петя!");
        }
    }
}
