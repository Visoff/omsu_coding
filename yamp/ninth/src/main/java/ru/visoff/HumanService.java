package ru.visoff;

import java.util.function.Function;
import java.util.function.Supplier;

public class HumanService {
    public static Supplier<String> GetFullNames(Supplier<Human> humanProducer) {
        Function<Human, String> fullName = (Human human) -> {
            return human.getSurname() + " " + human.getName() + " " + human.getPatronymic();
        };

        return () -> fullName.apply(humanProducer.get());
    }
}
