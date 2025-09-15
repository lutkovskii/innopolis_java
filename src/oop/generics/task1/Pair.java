
package oop.generics.task1;
//Класс, который умеет хранить и работать с двумя значениями любого типа
public class Pair<U, V> {
    private U firstValue;
    private V secondValue;

    public U getFirstValue() {
        return firstValue;
    }
    public V getSecondValue() {
        return secondValue;
    }
    public void setFirstValue(U value) {
        this.firstValue = value;
    }
    public void setSecondValue(V value) {
        this.secondValue = value;
    }
}
