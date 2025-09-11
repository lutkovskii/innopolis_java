package oop.collection.part1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);
//        for (Integer element : list){
//            if (element %2 !=0){
//                list.remove(element);
//            }
//        }
        //1 способ
//        list.removeIf(element -> element % 2 != 0);

        //2 способ
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            int element = iterator.next();
            if (element % 2 != 0){
                iterator.remove();
            }
        }
//3 способ
        for (int i =0; i < list.size(); i ++){
            if (list.get(i) % 2 != 0) {
                list.remove(list.get(i));
            }
        }
        System.out.println(list);
    }
}
