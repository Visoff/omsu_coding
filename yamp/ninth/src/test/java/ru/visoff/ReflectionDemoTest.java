package ru.visoff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.tools.javac.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ReflectionDemoTest {

    @Test
    void findHumanCountShouldCountHumanAndSubtypes() {
        List<Object> pool = new ArrayList<>();
        pool.add(new Human());
        pool.add(new Student()); // Student extends Human
        pool.add("Not a human");
        pool.add(new Object());

        long count = ReflectionDemo.findHumanCount(pool);
        assertEquals(2, count);
    }

    @Test
    void findHumanCountShouldReturnZeroWhenNoHumans() {
        List<Object> pool = List.of("abc", 123, new Object());
        assertEquals(0, ReflectionDemo.findHumanCount(pool));
    }

    @Test
    void getPublicFieldNamesShouldReturnAllPublicFields() {
        class WithFields {
            public int number;
            public String text;
            private double hidden;
        }
        WithFields obj = new WithFields();
        List<String> names = ReflectionDemo.getPublicFieldNames(obj);
        assertEquals(2, names.size());
        assertTrue(names.contains("number"));
        assertTrue(names.contains("text"));
    }

    @Test
    void getAllSuperClassNamesShouldIncludeObject() {
        List<String> hierarchy = ReflectionDemo.getAllSuperClassNames(new Student());
        assertEquals(3, hierarchy.size());
        assertTrue(hierarchy.get(0).endsWith("Student"));
        assertTrue(hierarchy.get(1).endsWith("Human"));
        assertTrue(hierarchy.get(2).endsWith("Object"));
    }

    @Test
    void executeAllPossibleShouldExecuteAndReturnCount() {
        List<Object> objects = new ArrayList<>();
        objects.add(new RunnableExecutable());
        objects.add(new SimpleExecutable());
        objects.add("Not executable");
        objects.add(new Human());

        long count = ReflectionDemo.executeAllPossible(objects);
        assertEquals(2, count);
        assertTrue(RunnableExecutable.executed);
        assertTrue(SimpleExecutable.executed);
    }

    @Test
    void getGettersAndSettersShouldReturnMethodNames() {
        class Sample {
            private int value;
            public int getValue() { return value; }
            public void setValue(int value) { this.value = value; }
            public String getName() { return "name"; }
            public void doSomething() {}
        }
        Sample obj = new Sample();
        List<String> result = ReflectionDemo.getGettersAndSetters(obj);
        assertTrue(result.contains("getValue"));
        assertTrue(result.contains("setValue"));
    }

    // Helper classes for tests
    static class Student extends Human {}

    static class RunnableExecutable implements Executable {
        static boolean executed = false;
        @Override
        public void execute() {
            executed = true;
        }
    }

    static class SimpleExecutable implements Executable {
        static boolean executed = false;
        @Override
        public void execute() {
            executed = true;
        }
    }
}
