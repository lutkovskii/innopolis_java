package oop.generics.lambda;

public class Main {
    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = new MyFunctionalInterface() {
            @Override
            public double getValue() {
                return 234;
            }
        };
        System.out.println(myFunctionalInterface.getValue());

        MyFunctionalInterface myFunctionalInterfaceLambda = () -> 234;
        System.out.println(myFunctionalInterfaceLambda.getValue());
        // ----------------------------------------------------------

        ReverseInterface reverseInterface = (str) -> new StringBuilder(str).reverse().toString();
        System.out.println(reverseInterface.getReversedString("Hello!"));

        MySuperInterface<String> reversedString = (str) -> new StringBuilder(str).reverse().toString();
        System.out.println(reversedString.func("Lambda"));

        MySuperInterface<Integer> factorial = (value) -> {
            int result = 1;
            for (int i = 1; i <= value; i++) {
                result *= i;
            }
            return result;
        };
        System.out.println(factorial.func(5));
    }
}
