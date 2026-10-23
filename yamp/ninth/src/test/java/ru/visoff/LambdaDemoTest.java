package ru.visoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaDemoTest {

    private final Human ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30, Sex.MALE);
    private final Student petrov = new Student("Petrov", "Petr", "Petrovich", 22, Sex.MALE,
            "HSE", "CS", "Software Engineering");

    @Test
    void stringLength() {
        assertEquals(0, LambdaDemo.STRING_LENGTH.apply(""));
        assertEquals(5, LambdaDemo.STRING_LENGTH.apply("hello"));
    }

    @Test
    void firstSymbol() {
        assertEquals('h', LambdaDemo.FIRST_SYMBOL.apply("hello"));
        assertNull(LambdaDemo.FIRST_SYMBOL.apply(""));
    }

    @Test
    void noSpaces() {
        assertTrue(LambdaDemo.NO_SPACES.test("hello"));
        assertTrue(LambdaDemo.NO_SPACES.test(""));
        assertFalse(LambdaDemo.NO_SPACES.test("hello world"));
    }

    @Test
    void countWordsInCommaSeparatedString() {
        assertEquals(1, LambdaDemo.COUNT_WORDS.apply("abc"));
        assertEquals(3, LambdaDemo.COUNT_WORDS.apply("one,two,three"));
        assertEquals(4, LambdaDemo.COUNT_WORDS.apply("a,b,c,d"));
    }

    @Test
    void personAge_worksWithHuman() {
        assertEquals(30, LambdaDemo.PERSON_AGE.apply(ivanov));
    }

    @Test
    void personAge_worksWithStudent() {
        assertEquals(22, LambdaDemo.PERSON_AGE.apply(petrov));
    }

    @Test
    void sameSurname_worksWithHuman() {
        Human clone = new Human("Ivanov", "Ilya", "Ilyich", 25, Sex.MALE);
        assertTrue(LambdaDemo.SAME_SURNAME.test(ivanov, clone));
        assertFalse(LambdaDemo.SAME_SURNAME.test(ivanov, petrov));
    }

    @Test
    void sameSurname_worksWithStudent() {
        Student sidorov = new Student("Sidorov", "Sidr", "Sidorovich", 20, Sex.MALE,
                "MSU", "Math", "Applied Math");
        assertTrue(LambdaDemo.SAME_SURNAME.test(ivanov, new Human("Ivanov", "X", "Y", 1, Sex.FEMALE)));
        assertFalse(LambdaDemo.SAME_SURNAME.test(petrov, sidorov));
    }

    @Test
    void fullName_worksWithHuman() {
        assertEquals("Ivanov Ivan Ivanovich", LambdaDemo.FULL_NAME.apply(ivanov));
    }

    @Test
    void fullName_worksWithStudent() {
        assertEquals("Petrov Petr Petrovich", LambdaDemo.FULL_NAME.apply(petrov));
    }

    @Test
    void olderByOneYear_createsNewHumanWithAgePlusOne() {
        Human older = LambdaDemo.OLDER_BY_ONE_YEAR.apply(ivanov);

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

        assertTrue(LambdaDemo.ALL_YOUNGER_THAN_AGE.test(a, b, c, 31));
        assertFalse(LambdaDemo.ALL_YOUNGER_THAN_AGE.test(a, b, c, 30));
        assertFalse(LambdaDemo.ALL_YOUNGER_THAN_AGE.test(a, b, c, 10));
    }
}
