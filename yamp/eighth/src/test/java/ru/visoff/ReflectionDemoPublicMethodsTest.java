package ru.visoff;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectionDemoPublicMethodsTest {

    private static class Sample {
        public void publicMethod() {
        }

        private void privateMethod() {
        }

        public static void publicStaticMethod() {
        }
    }

    @Test
    void getPublicMethodNames_containsExplicitPublicMethods() {
        List<String> names = ReflectionDemo.getPublicMethodNames(new Sample());

        assertTrue(names.contains("publicMethod"));
        assertTrue(names.contains("publicStaticMethod"));
    }

    @Test
    void getPublicMethodNames_doesNotContainPrivateMethods() {
        List<String> names = ReflectionDemo.getPublicMethodNames(new Sample());

        assertFalse(names.contains("privateMethod"));
    }

    @Test
    void getPublicMethodNames_containsInheritedObjectMethods() {
        List<String> names = ReflectionDemo.getPublicMethodNames(new Sample());

        assertTrue(names.contains("toString"));
        assertTrue(names.contains("hashCode"));
        assertTrue(names.contains("equals"));
        assertTrue(names.contains("getClass"));
    }

    @Test
    void getPublicMethodNames_listsAllPublicMethods() {
        List<String> names = ReflectionDemo.getPublicMethodNames(new Human());

        assertEquals(names.size(), new Human().getClass().getMethods().length);
    }
}
