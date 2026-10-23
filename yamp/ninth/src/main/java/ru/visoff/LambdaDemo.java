package ru.visoff;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaDemo {

    public static final Function<String, Integer> STRING_LENGTH = String::length;

    public static final Function<String, Character> FIRST_SYMBOL = s -> s.isEmpty() ? null : s.charAt(0);

    public static final Predicate<String> NO_SPACES = s -> !s.contains(" ");

    public static final Function<String, Integer> COUNT_WORDS = s -> s.split(",").length;

    public static final Function<Human, Integer> PERSON_AGE = Human::getAge;

    public static final BiPredicate<Human, Human> SAME_SURNAME = (h1, h2) -> h1.getSurname().equals(h2.getSurname());

    public static final Function<Human, String> FULL_NAME = h -> h.getSurname() + " " + h.getName() + " "
            + h.getPatronymic();

    public static final Function<Human, Human> OLDER_BY_ONE_YEAR = h -> new Human(h.getSurname(), h.getName(),
            h.getPatronymic(), h.getAge() + 1, h.getSex());

    public static final QuadPredicate<Human, Human, Human, Integer> ALL_YOUNGER_THAN_AGE = (h1, h2, h3,
            maxAge) -> h1.getAge() < maxAge && h2.getAge() < maxAge && h3.getAge() < maxAge;

    @FunctionalInterface
    public interface QuadPredicate<A, B, C, D> {
        boolean test(A a, B b, C c, D d);
    }
}
