package main;

import entity.User;
import service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserService userService) {
        return args -> {
            System.out.println("\n--- ШАГ 1: Проверка данных, загруженных из data.sql ---");
            System.out.println("Пользователи в БД: " + userService.getAllUsers());

            System.out.println("\n--- ШАГ 2: Создание новых пользователей ---");
            User user1 = userService.createUser("Иванчик Иванович4");
            User user2 = userService.createUser("Ivanchik Ivanovich4");
            System.out.println("Создан: " + user1);
            System.out.println("Создан: " + user2);

            System.out.println("\n--- ШАГ 3: Получение по ID ---");
            User foundUser = userService.getUserById(user1.getId());
            System.out.println("Найден пользователь по ID " + user1.getId() + ": " + foundUser.getUsername());

            System.out.println("\n--- ШАГ 4: Обновление пользователя ---");
            userService.updateUser(user1.getId(), "Петя Петров4");
            System.out.println("После обновления: " + userService.getUserById(user1.getId()));

            System.out.println("\n--- ШАГ 5: Удаление пользователя ---");
            userService.deleteUser(user2.getId());
            System.out.println("Пользователь с ID " + user2.getId() + " удален.");

            System.out.println("\n--- ШАГ 6: Финальный список пользователей ---");
            List<String> usernames = userService.getAllUsers().stream()
                    .map(User::getUsername)
                    .toList();
            System.out.println("Финальный список имен: " + usernames);

            System.out.println("\n--- ШАГ 7: Проверка кастомного метода поиска по имени ---");
            User user = userService.getByUserName("Начальный Пользователь 1");
            System.out.println("пользователь по имени -  " + user.toString());


        };
    }
}
