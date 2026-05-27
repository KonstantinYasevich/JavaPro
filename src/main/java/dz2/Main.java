package dz2;

import dz1.Test;
import dz1.TestResult;
import dz1.TestRunner;
import dz1.Tests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args)  {
        List<Integer> list = new ArrayList<>(List.of(5, 2, 10, 9, 4, 3, 10, 1, 13));
        //1
        getThirdMax(list);
        //2
        getThirdMaxDistinct(list);
        //3
        Employee employee1 = new Employee("Vasya", 50, "engineer");
        Employee employee2 = new Employee("Vova", 40, "engineer");
        Employee employee3 = new Employee("Vahid", 30, "engineer");
        Employee employee4 = new Employee("Vahtang", 20, "engineer");
        Employee employee5 = new Employee("Masha", 18, "secretary");
        List<Employee> employees = new ArrayList<>(List.of(employee1, employee2, employee3, employee4, employee5));
        getThreeOlderEngineers(employees);
    }

    private static int getThirdMax(List<Integer> list)
    {
       int result =  list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("В списке меньше 3 элементов"));
        System.out.println("3 максимум - " + result);
        return result;
    }

    private static int getThirdMaxDistinct(List<Integer> list)
    {
        int result =  list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("В списке меньше 3 элементов"));
        System.out.println("3 уникальный максимум - " + result);
        return result;
    }

    private static List<Employee> getThreeOlderEngineers(List<Employee> employees){
       List<Employee> result = employees.stream()
                .filter(e -> "engineer".equalsIgnoreCase(e.getPosition()))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .toList();

       result.stream()
               .map(e -> String.format("Имя: %s | Возраст: %d | Должность: %s", e.getName(), e.getAge(), e.getPosition()))
               .forEach(System.out::println);

       return result;
    }
}