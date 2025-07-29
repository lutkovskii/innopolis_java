package homeWork_5;
import java.util.Scanner;

public class homeWork_4_1 {

    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // Создаем строку с полной раскладкой клавиатуры
            String keyboard = "qwertyuiopasdfghjklzxcvbnm";

            System.out.print("Введите букву английского алфавита: ");
            String input = scanner.nextLine().trim();

            if (input.length() != 1) {
                System.out.println("Ошибка: введите ровно один символ");
                scanner.close();
                return;
            }

            char letter = Character.toLowerCase(input.charAt(0));

            // Проверяем, что это английская буква
            if (letter < 'a' || letter > 'z') {
                System.out.println("Ошибка: введите английскую букву");
                scanner.close();
                return;
            }

            // Находим позицию буквы
            int position = keyboard.indexOf(letter);

            if (position == -1) {
                System.out.println("Ошибка: символ не найден");
                scanner.close();
                return;
            }

            // Находим левого соседа с учетом цикличности
            int leftPosition;
            if (position == 0) {
                // Для первой буквы берем последнюю
                leftPosition = keyboard.length() - 1;
            } else {
                leftPosition = position - 1;
            }

            char result = keyboard.charAt(leftPosition);
            System.out.println(result);

            scanner.close();
    }
}