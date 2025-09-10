package homeWork_8.homeWork_8_3;
import java.util.*;

public class PowerfulSet {

    public <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            return new HashSet<>();
        }

        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    public <T> Set<T> union(Set<T> set1, Set<T> set2) {
        if (set1 == null && set2 == null) {
            return new HashSet<>();
        }
        if (set1 == null) {
            return new HashSet<>(set2);
        }
        if (set2 == null) {
            return new HashSet<>(set1);
        }

        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    public <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) {
        if (set1 == null) {
            return new HashSet<>();
        }
        if (set2 == null) {
            return new HashSet<>(set1);
        }

        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }

    public static void main(String[] args) {
        PowerfulSet ps = new PowerfulSet();

        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(0, 1, 2, 4));

        System.out.println("Пересечение: " + ps.intersection(set1, set2));
        System.out.println("Объединение: " + ps.union(set1, set2));
        System.out.println("Дополнение: " + ps.relativeComplement(set1, set2));
    }
}