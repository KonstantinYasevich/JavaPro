package dz1;

import dz1.custom_extensions.BadTestClassError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import dz1.custom_annotations.Order;
import dz1.custom_annotations.Test;
import dz1.custom_extensions.TestAssertionError;

public class TestRunner {
    public static Map<TestResult, List<dz1.Test>> runTests(Class<?> c) {
        Object testClassInsance = new ArrayList<>();
        List<Method> beforeSuote = new ArrayList<>();
        List<Method> afterSuote = new ArrayList<>();
        List<Method> beforeEach = new ArrayList<>();
        List<Method> afterEach = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<dz1.Test> testsResult = new ArrayList<>();
        Map<TestResult, List<dz1.Test>> result = new HashMap<>();

        try {
            testClassInsance = c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadTestClassError("не возможно создать экземпляр класса");
        }
        for (Method method : c.getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            System.out.println("Метод " + method.getName());
            for (Annotation annotation : annotations) {

                if (annotation.annotationType().getSimpleName().equals("Test")) {
                    tests.add(method);
                    if (isStatic(method)) {
                        throw new BadTestClassError("метод с аннотацией " + annotation.annotationType().getSimpleName() + " не должен быть статичным");
                    }
                }

                if (annotation.annotationType().getSimpleName().equals("BeforeEach")) {
                    beforeEach.add(method);
                    if (isStatic(method)) {
                        throw new BadTestClassError("метод с аннотацией " + annotation.annotationType().getSimpleName() + " не должен быть статичным");
                    }
                }

                if (annotation.annotationType().getSimpleName().equals("AfterEach")) {
                    afterEach.add(method);
                    if (isStatic(method)) {
                        throw new BadTestClassError("метод с аннотацией " + annotation.annotationType().getSimpleName() + " не должен быть статичным");
                    }
                }

                if (annotation.annotationType().getSimpleName().equals("BeforeSuite")) {
                    beforeSuote.add(method);
                    if (!isStatic(method)) {
                        throw new BadTestClassError("метод с аннотацией " + annotation.annotationType().getSimpleName() + " должен быть статичным");
                    }
                }

                if (annotation.annotationType().getSimpleName().equals("AfterSuite")) {
                    afterSuote.add(method);
                    if (!isStatic(method)) {
                        throw new BadTestClassError("метод с аннотацией " + annotation.annotationType().getSimpleName() + " должен быть статичным");
                    }
                }
                System.out.println("Найдена аннотация: " + annotation.annotationType().getSimpleName());
                System.out.println(tests.stream().count());
            }
        }

        try {

            //Запускаем все BeforeSuite
            for (Method method : beforeSuote) {
                method.invoke(testClassInsance);
            }

            //Сортируем тесты по приоритету
            tests = testsSort(tests);
            //Запускаем Test с Before/AterEach
            for (Method method : tests) {
                //Before
                for (Method methodBefore : beforeEach) {
                    methodBefore.invoke(testClassInsance);
                }
                //Test
                dz1.Test test = new dz1.Test();
                String testName = method.getAnnotation(Test.class).testName();
                if (testName.isEmpty()) {
                    test.setTestName(method.getName());
                } else {
                    test.setTestName(testName);
                }
                try {
                    method.invoke(testClassInsance);
                    test.setTypeResult(TestResult.SUCCESS);
                } catch (java.lang.reflect.InvocationTargetException e) {
                    Throwable originalException = e.getCause();
                    test.setExtension(originalException.getMessage());
                    if (originalException instanceof TestAssertionError) {
                        test.setTypeResult(TestResult.FAILED);
                    } else {
                        test.setTypeResult(TestResult.ERROR);
                    }
                } catch (Exception e) {
                    test.setExtension(e.getMessage());
                    test.setTypeResult(TestResult.ERROR);
                } finally {
                    TestResult currentResultType = test.getTyoeResult();
                    if (!result.containsKey(currentResultType)) {
                        result.put(currentResultType, new ArrayList<>());
                    }
                    result.get(currentResultType).add(test);
                }


                //After
                for (Method methodAfter : afterEach) {
                    methodAfter.invoke(testClassInsance);
                }
            }

            //Запускаем все AfterSuite
            for (Method method : afterSuote) {
                method.invoke(testClassInsance);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    private static Boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    private static Integer getPriority(Annotation annotation) {
        if (annotation == null) return null;
        try {
            var priorityMethod = annotation.annotationType().getMethod("priority");
            return (Integer) priorityMethod.invoke(annotation);
        } catch (Exception e) {
            return null;
        }
    }

    private static List<Method> testsSort(List<Method> tests) {
        //Проверим диапазон ордер
        for (Method method : tests) {
            if (method.isAnnotationPresent(Order.class)) {
                int orderValue = method.getAnnotation(Order.class).value();

                // Проверяем жесткий диапазон от 1 до 10
                if (orderValue < 1 || orderValue > 10) {
                    throw new BadTestClassError("Ошибка в методе " + method.getName() +
                            ": значение @Order должно быть в диапазоне от 1 до 10, а передано: " + orderValue);
                }
            }
        }

        //сортировка по приоритету и имени если равны
        tests.sort(
                Comparator.<Method, Integer>comparing(m -> m.getAnnotation(Test.class).priority())
                        .reversed()
                        .thenComparing(m -> {
                            String testName = m.getAnnotation(Test.class).testName();
                            return testName.isEmpty() ? m.getName() : testName;
                        })
        );

        //сортировка по порядку
        tests.sort(
                Comparator.comparing(m -> m.isAnnotationPresent(Order.class)
                        ? m.getAnnotation(Order.class).value()
                        : 11)
        );
        return tests;
    }
}

