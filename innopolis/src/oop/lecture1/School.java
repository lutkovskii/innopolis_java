package oop.lecture1;

public class School {
    private final String title;
    private final String address;

    public School(String title, String address){
        this.title = title;
        this.address = address;
    }

    //public School(String title){
//        this.title = title;
//    }

    @Override
    public String toString() {
        return "School{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
