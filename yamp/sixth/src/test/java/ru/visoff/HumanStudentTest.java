package ru.visoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HumanStudentTest {

    @Test
    void humanConstructorAndGetters() {
        Human h = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        assertEquals("Ivanov", h.getSurname());
        assertEquals("Ivan", h.getName());
        assertEquals("Ivanovich", h.getPatronymic());
        assertEquals(30, h.getAge());
    }

    @Test
    void humanDefaultConstructor() {
        Human h = new Human();
        assertEquals("Unknown", h.getSurname());
        assertEquals("Unknown", h.getName());
        assertEquals("Unknown", h.getPatronymic());
        assertEquals(0, h.getAge());
    }

    @Test
    void humanSettersAndValidation() {
        Human h = new Human();
        h.setSurname("Petrov");
        assertEquals("Petrov", h.getSurname());
        assertThrows(NullPointerException.class, () -> h.setSurname(null));

        h.setName("Petr");
        assertEquals("Petr", h.getName());
        assertThrows(NullPointerException.class, () -> h.setName(null));

        h.setPatronymic("Petrovich");
        assertEquals("Petrovich", h.getPatronymic());
        assertThrows(NullPointerException.class, () -> h.setPatronymic(null));

        h.setAge(25);
        assertEquals(25, h.getAge());
        assertThrows(NullPointerException.class, () -> h.setAge(null));
        assertThrows(IllegalArgumentException.class, () -> h.setAge(-1));
    }

    @Test
    void humanEqualsAndHashCode() {
        Human h1 = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        Human h2 = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        Human h3 = new Human("Petrov", "Petr", "Petrovich", 25);

        assertEquals(h1, h2);
        assertNotEquals(h1, h3);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1.hashCode(), h3.hashCode());
    }

    @Test
    void studentConstructorAndGetters() {
        Student s = new Student("Ivanov", "Ivan", "Ivanovich", 30, "CS");
        assertEquals("Ivanov", s.getSurname());
        assertEquals("Ivan", s.getName());
        assertEquals("Ivanovich", s.getPatronymic());
        assertEquals(30, s.getAge());
        assertEquals("CS", s.getFaculty());
    }

    @Test
    void studentDefaultConstructor() {
        Student s = new Student();
        assertEquals("Unknown", s.getSurname());
        assertEquals("Unknown", s.getName());
        assertEquals("Unknown", s.getPatronymic());
        assertEquals(0, s.getAge());
        assertEquals("Unknown", s.getFaculty());
    }

    @Test
    void studentSettersAndValidation() {
        Student s = new Student();
        s.setFaculty("Math");
        assertEquals("Math", s.getFaculty());
        assertThrows(NullPointerException.class, () -> s.setFaculty(null));
    }

    @Test
    void studentEqualsAndHashCode() {
        Student s1 = new Student("Ivanov", "Ivan", "Ivanovich", 30, "CS");
        Student s2 = new Student("Ivanov", "Ivan", "Ivanovich", 30, "CS");
        Student s3 = new Student("Ivanov", "Ivan", "Ivanovich", 30, "Math");
        Human h = new Human("Ivanov", "Ivan", "Ivanovich", 30);

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, h);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }
}
