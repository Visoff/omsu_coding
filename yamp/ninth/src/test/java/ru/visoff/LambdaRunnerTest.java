package ru.visoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaRunnerTest {
    private final Human ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30, Sex.MALE);
    private final Student petrov = new Student("Petrov", "Petr", "Petrovich", 22, Sex.MALE,
            "HSE", "CS", "Software Engineering");

    @Test
    void stringLength() {
        assertEquals(0, LambdaRunner.run(LambdaDemo.STRING_LENGTH, ""));
        assertEquals(5, LambdaRunner.run(LambdaDemo.STRING_LENGTH, "hello"));
    }

    @Test
    void firstSymbol() {
        assertEquals('h', LambdaRunner.run(LambdaDemo.FIRST_SYMBOL, "hello"));
        assertNull(LambdaRunner.run(LambdaDemo.FIRST_SYMBOL, ""));
    }

    @Test
    void noSpaces() {
        assertTrue(LambdaRunner.run(LambdaDemo.NO_SPACES, "hello"));
        assertTrue(LambdaRunner.run(LambdaDemo.NO_SPACES, ""));
        assertFalse(LambdaRunner.run(LambdaDemo.NO_SPACES, "hello world"));
    }

    @Test
    void countWordsInCommaSeparatedString() {
        assertEquals(1, LambdaRunner.run(LambdaDemo.COUNT_WORDS, "abc"));
        assertEquals(3, LambdaRunner.run(LambdaDemo.COUNT_WORDS, "one,two,three"));
        assertEquals(4, LambdaRunner.run(LambdaDemo.COUNT_WORDS, "a,b,c,d"));
    }

    @Test
    void personAge_worksWithHuman() {
        assertEquals(30, LambdaRunner.run(LambdaDemo.PERSON_AGE, ivanov));
    }

    @Test
    void personAge_worksWithStudent() {
        assertEquals(22, LambdaRunner.run(LambdaDemo.PERSON_AGE, petrov));
    }

    @Test
    void sameSurname_worksWithHuman() {
        Human clone = new Human("Ivanov", "Ilya", "Ilyich", 25, Sex.MALE);
        assertTrue(LambdaRunner.run(LambdaDemo.SAME_SURNAME, ivanov, clone));
        assertFalse(LambdaRunner.run(LambdaDemo.SAME_SURNAME, ivanov, petrov));
    }

    @Test
    void sameSurname_worksWithStudent() {
        Student sidorov = new Student("Sidorov", "Sidr", "Sidorovich", 20, Sex.MALE,
                "MSU", "Math", "Applied Math");
        assertTrue(LambdaRunner.run(LambdaDemo.SAME_SURNAME, ivanov, new Human("Ivanov", "X", "Y", 1, Sex.FEMALE)));
        assertFalse(LambdaRunner.run(LambdaDemo.SAME_SURNAME, petrov, sidorov));
    }

    @Test
    void fullName_worksWithHuman() {
        assertEquals("Ivanov Ivan Ivanovich", LambdaRunner.run(LambdaDemo.FULL_NAME, ivanov));
    }

    @Test
    void fullName_worksWithStudent() {
        assertEquals("Petrov Petr Petrovich", LambdaRunner.run(LambdaDemo.FULL_NAME, petrov));
    }

    @Test
    void olderByOneYear_createsNewHumanWithAgePlusOne() {
        Human older = LambdaRunner.run(LambdaDemo.OLDER_BY_ONE_YEAR, ivanov);

        assertNotSame(ivanov, older);
        assertEquals(31, older.getAge());
        assertEquals(30, ivanov.getAge());
        assertEquals("Ivanov", older.getSurname());
        assertEquals(Sex.MALE, older.getSex());
    }

    @Test
    void allYoungerThanAge() {
        Human a = new Human("A", "A", "A", 10, Sex.MALE);
        Human b = new Human("B", "B", "B", 20, Sex.MALE);
        Human c = new Human("C", "C", "C", 30, Sex.MALE);

        assertTrue(LambdaRunner.run(LambdaDemo.ALL_YOUNGER_THAN_AGE, a, b, c, 31));
        assertFalse(LambdaRunner.run(LambdaDemo.ALL_YOUNGER_THAN_AGE, a, b, c, 30));
        assertFalse(LambdaRunner.run(LambdaDemo.ALL_YOUNGER_THAN_AGE, a, b, c, 10));
    }
}
