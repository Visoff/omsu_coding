package ru.visoff;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

public class ReflectionDemo {
    public static long findHumanCount(List<Object> pool) {
        return pool.stream().filter(obj -> obj instanceof Human).count();
    }

    public static List<String> getPublicFieldNames(Object obj) {
        return List.of(obj.getClass().getFields()).stream().map(f -> f.getName()).toList();
    }

    public static List<String> getAllSuperClassNames(Object obj) {
        List<String> result = new java.util.ArrayList<>();
        Class<?> clazz = obj.getClass();
        while (clazz != Object.class) {
            result.add(clazz.getName());
            clazz = clazz.getSuperclass();
        }
        result.add(clazz.getName());
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
        Predicate<Method> getterCond = f -> f.getReturnType() != void.class && f.getParameterCount() == 0;
        Predicate<Method> setterCond = f -> f.getReturnType() == void.class && f.getParameterCount() == 1;
        return fields.stream()
                .filter(getterCond.or(setterCond))
                .map(Method::getName).filter(s -> s.startsWith("get") || s.startsWith("set")).toList();
    }
}
