package homeWork_8.homeWork_8_2;

import java.util.*;

public class AnagramChecker {

    public static boolean isAnagram(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        s = s.toLowerCase().replaceAll("\\s+", "");
        t = t.toLowerCase().replaceAll("\\s+", "");

        if (s.length() != t.length()) {
            return false;
        }

        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        Arrays.sort(sArray);
        Arrays.sort(tArray);

        return Arrays.equals(sArray, tArray);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите первую строку:");
        String s = scanner.nextLine();

        System.out.println("Введите вторую строку:");
        String t = scanner.nextLine();

        boolean result = isAnagram(s, t);
        System.out.println(result);

        scanner.close();
    }
}