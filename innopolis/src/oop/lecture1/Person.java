package oop.lecture1;

public class Person {
    String fio;
    String address;
            public Person(String fio, String address){
                this.address = address;
                this.fio = fio;
            }

    @Override
    public String toString() {
        return "Person{" +
                "fio='" + fio + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}