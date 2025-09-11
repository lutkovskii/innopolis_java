package oop.collection.part1;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("слон");
        words.add("слон");
        words.add("кот");
        words.add("мышь");
        words.add("кот");
        words.add("собака");
        words.add("кот");
        words.add("мышь");
//слон: 2. мышь: 2 ...
        System.out.println(wordsCount(words));
    }
    public static Map<String, Integer> wordsCount(List<String>words){
        Map<String, Integer> result = new HashMap<>();
        for (String str : words){
            int counter = 1;
            if (result.containsKey(str)){
                counter = result.get(str);
                counter ++;
            }
            result.put(str, counter);
        }
        return result;
    }
}
