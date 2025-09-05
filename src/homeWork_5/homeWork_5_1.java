package homeWork_5;
import java.util.Scanner;

public class homeWork_5_1 {

    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            String keyboard = "qwertyuiopasdfghjklzxcvbnm";

            System.out.print("Введите букву английского алфавита: ");
            String input = scanner.nextLine().trim();

            if (input.length() != 1) {
                System.out.println("Ошибка: введите ровно один символ");
                scanner.close();
                return;
            }

            char letter = Character.toLowerCase(input.charAt(0));

            if (letter < 'a' || letter > 'z') {
                System.out.println("Ошибка: введите английскую букву");
                scanner.close();
                return;
            }

            int position = keyboard.indexOf(letter);

            if (position == -1) {
                System.out.println("Ошибка: символ не найден");
                scanner.close();
                return;
            }

            int leftPosition;
            if (position == 0) {
                leftPosition = keyboard.length() - 1;
            } else {
                leftPosition = position - 1;
            }

            char result = keyboard.charAt(leftPosition);
            System.out.println(result);

            scanner.close();
    }
}