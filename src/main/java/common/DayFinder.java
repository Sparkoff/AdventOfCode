package common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;


public class DayFinder {
    private Set<String> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null) throw new RuntimeException("No Day in this package");

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class") && !line.contains("$"))
                .collect(Collectors.toSet());
    }

    public DayBase getLastDayIn(String year) {
        Set<String> availableClasses = findAllClassesUsingClassLoader(year);
        String lastDay = availableClasses.stream()
                .max(Comparator.comparingInt(d -> Integer.parseInt(d.substring(3, d.length() - 6))))
                .orElseThrow();
        try {
            return getClass(lastDay, year).getConstructor().newInstance();
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<DayBase> getClass(String className, String packageName) {
        try {
            @SuppressWarnings("unchecked") Class<DayBase> dayBaseClass = (Class<DayBase>) Class.forName(
                    String.format("%s.%s",
                            packageName,
                            className.substring(0, className.lastIndexOf('.'))));
            return dayBaseClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
