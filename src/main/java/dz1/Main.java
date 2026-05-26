package dz1;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Map<TestResult, List<Test>> result = TestRunner.runTests(Tests.class);
        System.out.println(result.toString());

    }
}