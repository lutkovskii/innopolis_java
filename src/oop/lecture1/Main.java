package oop.lecture1;

public class Main {
    public static void main(String[] args) {
        School school = new School("Школа 556", "ул. Якубовича д. 47");
        Person myPerson = new Student("Петухов Максим", "Барселона, ул. Чахохбили", school);
        System.out.println(myPerson);
    }
}
//title:"Школа 556", address:"ул. Якубовича д. 47"