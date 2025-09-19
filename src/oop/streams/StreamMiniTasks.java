package oop.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamMiniTasks {
    public static void main(String[] args) {
        // 1. фильтрация: оставить слово на 'b' длиной >= 4
        {
            List<String> words = List.of("bee", "book", "at", "ball", "be", "bonus");
            List<String> result = words.stream()
                    .filter(w -> w.startsWith("b"))
                    .filter(w -> w.length() >= 4)
                    .toList();
            System.out.println("1)" + result);
        }

        // квадрат чисел (map)
        {
            List<Integer> digits = List.of(1, 2, 3, 4, 5);
            List<Integer> result = digits.stream()
                    .map(x -> x * x)
                    .toList();

            List<Integer> res2 = IntStream.of(1, 2, 3, 4, 5)
                    .map(x -> x * x)
                    .boxed() //преобразует IntStream -> Stream<Integer>
                    .toList();
            System.out.println("2) " + result);
            System.out.println("2) " + res2);
        }

        // 3) flatMap: уникальное слово из фраз
        {
            List<String> phrases = List.of("java streams", "hello world", "java lambdas");
            List<String> r = phrases.stream()
                    .flatMap(p -> Arrays.stream(p.split("\\s+")))
                    .map(String::toLowerCase)
                    .distinct()
                    .sorted()
                    .toList();
            System.out.println("3) " + r);
        }

        // 4) убрать дубли
        {
            List<Integer> nums = List.of(1, 2, 2, 3, 3, 4, 5, 5, 6);
            System.out.println("4) " + nums.stream().distinct().toList());
        }

        // 5) sorted: по длине, затем по алфавиту
        {
            List<String> w = List.of("pear", "apple", "kiwi", "banana");
            List<String> res = w.stream()
                    .sorted(Comparator.comparingInt(String::length)
                            .thenComparing(Comparator.naturalOrder()))
                    .toList();
            System.out.println("5) " + res);

        }

        // 6) skip + limit: пропустить 2, взять 3
        {
            List<Integer> digits = List.of(10, 20, 30, 40, 50, 60).stream()
                    .skip(2)
                    .limit(3)
                    .toList();
            System.out.println("6) " + digits);
        }

        // 7) takeWhile / dropWhile
        {
            List<Integer> src = List.of(1, 2, 3, 0, 4, 5);
            var take = src.stream().takeWhile(n -> n < 3).toList(); // берем элементы сначала, пока n < 3
            var drop = src.stream().dropWhile(n -> n < 3).toList(); // пропускаем элементы сначала, пока n < 3
            System.out.println("7) take=" + take + " drop=" + drop);
        }

        // 8) peek: "подглядывание" в поток (для отладки)
        {
            List<Integer> r = IntStream.rangeClosed(1, 6).boxed()
                    .peek(n -> {
                    })
                    .filter(n -> n % 2 == 0)
                    .peek(n -> System.out.println("peek: " + n + " "))
                    .toList();
            System.out.println("8) " + r);
        }

        // 9) суммарная длина слов (mapToInt + sum)
        {
            int sum = Stream.of("a", "ab", "abc")
                    .mapToInt(String::length)
                    .sum();
            System.out.println("9) " + sum);
        }

        // 10) сводка по числам
        {
            IntSummaryStatistics st = IntStream.of(3, 7, 2, 10)
                    .summaryStatistics(); //считаем sum/count/avg/min/max
            System.out.println("10) " + st);
        }

        // 11) findFist / Optional
        {
            Optional<String> fistLen = Stream.of("a", "bb", "ccc")
                    .filter(s -> s.length() == 2)
                    .findFirst();
            System.out.println("11) " + fistLen.orElse("not found"));
        }
        // 12) anyMatch / allMatch / noneMatch
        {
            List<Integer> l = List.of(1, 2, 3, 4);
            boolean any = l.stream().anyMatch(n -> n > 3); //есть ли элементы больше 3?
            boolean all = l.stream().allMatch(n -> n < 5); //все ли элементы больше 5?
            boolean none = l.stream().noneMatch(n -> n < 0); //нет ли элементов меньше 0?
            System.out.println("12) any=" + any + " all=" + all + " none=" + none);
        }

        // 13) min/max с компаратором
        {
            List<String> w = List.of("alpha", "bet", "gamma");
            String minLen = w.stream()
                    .min(Comparator.comparingInt(String::length))
                    .orElse("");
            System.out.println("13) " + minLen);
        }

        // 14: reduce - произведение чисел
        {
            int prod = Stream.of(1, 2, 3, 4, 5)
                    .reduce(1, (a, b) -> a * b);
            System.out.println("14) " + prod);
        }

        // 15: collect (joining)
        {
            String csv = Stream.of("a", "b", "c", "d")
                    .collect(Collectors.joining(","));
            System.out.println("15) " + csv);
        }

        // 16) iterate / generate - первые 5 четных чисел
        {
            List<Integer> evens = Stream.iterate(0, n -> n + 2)
                    .limit(5)
                    .toList();
            System.out.println("16) " + evens);
        }

        //17) параллельные стримы
        {
            System.out.println("17) Параллельный стрим");
            IntStream.rangeClosed(1, 10)
                    .parallel()
                    .forEach(System.out::print);
            System.out.println("(порядок отличается)");
        }

        // 18) сохранение порядка в параллельном стриме
        {
            System.out.println("18) Параллельный стрим ordered");
            IntStream.rangeClosed(1, 10)
                    .parallel()
                    .forEachOrdered(System.out::print);
            System.out.println();
        }

        //19) parallel reduce
        {
            int sumSquares = IntStream.rangeClosed(1, 5)
                    .parallel()
                    .map(x -> x * x)
                    .sum();
            System.out.println("19) " + sumSquares);
        }

    }
}