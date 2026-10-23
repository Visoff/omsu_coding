package ru.visoff;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaRunner {
    public static <T, R> R run(Function<T, R> function, T value) {
        return function.apply(value);
    }

    public static <T> boolean run(Predicate<T> predicate, T value) {
        return predicate.test(value);
    }

    public static <A, B> boolean run(BiPredicate<A, B> predicate, A a, B b) {
        return predicate.test(a, b);
    }

    public static <A, B, C, D> boolean run(LambdaDemo.QuadPredicate<A, B, C, D> predicate, A a, B b, C c, D d) {
        return predicate.test(a, b, c, d);
    }
}
