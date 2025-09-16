package oop.generics.lambda;

@FunctionalInterface
public interface MySuperInterface<T> {
    T func(T value);
}
