package oop.collection.part2.stream;

public class Palindrom {
    public static void main(String[] args) {
        System.out.println(isPalindrom(",aB121be"));
    }
    public static boolean isPalindrom(String from){
        StringBuilder leftToRight = new StringBuilder();
        from.chars()
                .filter(Character::isLetterOrDigit)
                .map(Character::toLowerCase)
                .forEach(leftToRight::appendCodePoint);
        System.out.println("leftToRight: " +leftToRight);
        StringBuilder rightToLeft = new StringBuilder(leftToRight.reverse());
        return rightToLeft.toString().equals(leftToRight.toString());
    }
}
