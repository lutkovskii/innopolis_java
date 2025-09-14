package oop.collection.part2.stream;

import java.util.List;

public class Tas1 {
    public static void main(String[] args) {
        List<String> myPlaces = List.of("Nepal, Pokhara ", "Nepal, Kathmandu ",  "India, Delhi", "USA, New York", "Africa, Nigeria");
        myPlaces.stream()
                .filter(place -> place.startsWith("Nepal"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }
}
