package oop.lecture3;
import oop.lecture3.reflection.D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
/*
interface A extends U{}
interface B {}
interface U {}
class C implements A {}
class D extends C implements S {}
*/

/*
Получить все интерфейсы класса, включая интерфейсы от классов-родителей.
Не включать интерфейсы родительских интерфейсов.
 */
public class Reflection {
    public static void main(String[] args) {
        List<Class<?>> result = getAllInterfaces(D.class);
        result.forEach(res -> System.out.println(res.getName()));

    }
    private static List<Class<?>> getAllInterfaces(Class<?> cls) {
        List<Class<?>> interfaces = new ArrayList<>();
        System.out.println(Arrays.toString(cls.getInterfaces()));
        while (cls != Object.class){
            interfaces.addAll(Arrays.asList(cls.getInterfaces()));
            cls = cls.getSuperclass();
        }
        return interfaces;
    }
}
