package homeWork_5;
import java.util.Arrays;
import java.util.Scanner;

public class homeWork_5_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine().trim();

        String[] words = line.split(" ");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            String word = words[i].toLowerCase();
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            result.append(new String(chars));
        }

        System.out.println(result.toString());
    }
}