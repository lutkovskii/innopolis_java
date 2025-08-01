package homeWork_5;
import java.util.Scanner;

public class homeWork_5_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().trim();

        String arrow1 = ">>-->";
        String arrow2 = "<--<<";
        int count = 0;
        int n = s.length();

        for (int i = 0; i <= n - 5; i++) {
            String substr = s.substring(i, i + 5);
            if (substr.equals(arrow1) || substr.equals(arrow2)) {
                count++;
            }
        }

        System.out.println(count);
    }
}