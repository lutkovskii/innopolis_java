package oop.collection.part2.stream;
//Сумма четных чисел в промежутке от 1 до 100 включительно

import java.util.stream.IntStream;

public class SumNumbers {
    public static void main(String[] args) {
        System.out.println(
                IntStream.range(1, 101)
                        .filter(i -> i%2 == 0)
                       // .sum() //можно и так
                        .reduce(0, Integer :: sum)

        );
    }

}
