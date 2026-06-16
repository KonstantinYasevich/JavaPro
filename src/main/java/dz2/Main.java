package dz2;

import dz1.Test;
import dz1.TestResult;
import dz1.TestRunner;
import dz1.Tests;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


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
        //4
        getAverageAge(employees);
        //5
        List<String> words = new ArrayList<>(List.of("word", "pneumonoultramicroscopicsilicovolcanoconiosis", "number", "dog", "cat"));
        getLongWord(words);
        //6
        String sentence = "Косил косой косой косой";
        wordCount(sentence);
        List<String> wordsForSort = Arrays.asList("яблоко", "арбуз", "банан", "еж", "дом", "кот");
        wordDoubleSort(wordsForSort);
        String[] lines = {
                "яблоко арбуз банан еж дом",
                "автомобиль кот пес окно дерево",
                "мама мыла раму очень чисто"
        };
        longestWord(lines);

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

    private static double getAverageAge(List<Employee> employees) {
        double result = employees.stream()
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0.0);
        System.out.println("Средний возраст - " + result);
        return result;
    }

    private static String getLongWord(List<String> words) {
        String result = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        System.out.println("Самое длинное слово - " + result);
        return result;
    }

    private static HashMap<String, Integer> wordCount(String words){
        HashMap<String, Integer> result = new HashMap<>();
        Arrays.stream(words.split(" "))
                .map(String::toLowerCase)
                .forEach(word -> result.put(word, result.getOrDefault(word, 0) + 1));
        System.out.println(result);
        return result;
    }

    private static void wordDoubleSort(List<String> words){
        words.stream()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    private static String longestWord(String[] words){
        String longestWord = Arrays.stream(words)
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        System.out.println("Самое длинное слово - " + longestWord);
        return longestWord;
    }

}