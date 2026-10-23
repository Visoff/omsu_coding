package ru.visoff;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ReflectionDemo {
    public static boolean hasSuperImplementingInitInterface(Object obj) {
        return Arrays.stream(obj.getClass().getInterfaces()).anyMatch(
                (Class<?> cls) -> {
                    try {
                        cls.getMethod("init");
                        return true;
                    } catch (NoSuchMethodException e) {
                        return false;
                    }
                });
    }

    public static long findHumanCount(List<Object> pool) {
        return pool.stream().filter(obj -> obj instanceof Human).count();
    }

    public static List<String> getPublicFieldNames(Object obj) {
        return List.of(obj.getClass().getFields()).stream().map(f -> f.getName()).toList();
    }

    public static List<String> getPublicMethodNames(Object obj) {
        return List.of(obj.getClass().getMethods()).stream().map(Method::getName).toList();
    }

    public static List<String> getAllSuperClassNames(Object obj) {
        List<String> result = new java.util.ArrayList<>();
        Class<?> curr = obj.getClass();
        while (curr != Object.class) {
            result.add(curr.getName());
            curr = curr.getSuperclass();
        }
        result.add(curr.getName());
        return result;
    }

    public static long executeAllPossible(List<Object> objcts) {
        List<Executable> executables = objcts.stream().filter(obj -> obj instanceof Executable)
                .map(obj -> (Executable) obj).toList();
        executables.forEach(Executable::execute);
        return executables.size();
    }

    public static List<String> getGettersAndSetters(Object obj) {
        List<Method> fields = List.of(obj.getClass().getMethods());
        Predicate<Method> getterCond = f -> f.getReturnType() != void.class && f.getParameterCount() == 0
                && f.getName().startsWith("get");
        Predicate<Method> setterCond = f -> f.getReturnType() == void.class && f.getParameterCount() == 1
                && f.getName().startsWith("set");
        return fields.stream()
                .filter(getterCond.or(setterCond))
                .map(Method::getName).toList();
    }
}
