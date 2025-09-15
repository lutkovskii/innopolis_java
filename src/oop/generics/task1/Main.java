package oop.generics.task1;

public class Main {
    public static void main(String[] args) {
        Pair<String, Integer> myPair = new Pair<>();
        myPair.setFirstValue("Test");
        myPair.setSecondValue(123);
        System.out.println(myPair.getFirstValue());
        System.out.println(myPair.getSecondValue());
    }
}
