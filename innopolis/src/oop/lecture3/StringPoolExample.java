package oop.lecture3;

public class StringPoolExample {
    public static void main(String[] args) {
        String str1 = "TopJava";
        String str2 = "TopJava";
        String str3 = (new String("TopJava")).intern();
        String str4 = (new String("TopJava")).intern();

        System.out.println("Строка 1 равн строке 2? " + (str1 == str2));
        System.out.println("Строка 2 равн строке 3? " + (str2 == str3));
        System.out.println("Строка 3 равн строке 4? " + (str3 == str4));

    }
}
