package homeWork_8.homeWork_8_1;
import java.util.*;

public class UniqueElements {

    public static <T> Set<T> getUniqueElements(ArrayList<T> list) {
        if (list == null) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(list);
    }


    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>(Arrays.asList("a", "b", "a", "c", "b", "d"));
        Set<String> unique = getUniqueElements(input);
        System.out.println(unique);
    }
}