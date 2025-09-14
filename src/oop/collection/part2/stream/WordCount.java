package oop.collection.part2.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordCount {
    public static void main(String[] args) {
        List<String> names = List.of("Sam", "Peter", "Sam", "Andrei");

        ///1
        Set<String> unique = new HashSet<>(names);
        for (String key : unique){
            System.out.println(key + ": " + Collections.frequency(names, key));
        }

        //2
        Map<String, Long> result = names.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);

        /*
        select name, count(*)
        from names
        group by name;

        1 row: Andrei -> 1
        2 row: Peter -> 1
        3 row: Peter -> 1
        ------------------
        1 row: Andrei -> 1
        2 row: Peter -> 2
         */



    }
}
