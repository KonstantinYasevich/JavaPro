package dz4;

import dz4.config.DataSourceConfig;
import dz4.pojo.User;
import dz4.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan(basePackages = "dz4")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        UserService userService = context.getBean(UserService.class);

        User user1 = userService.createUser("Иванчик Иванович1");
        User user2 = userService.createUser("Ivanchik Ivanovich1");

        List<User> allUsers = userService.getAllUsers();
        System.out.println("Список всех пользователей в БД: " + allUsers);

        User foundUser = userService.getUserById(user1.getId());
        System.out.println("Найден пользователь по ID " + user1.getId() + ": " + foundUser.getUsername());

        userService.updateUser(user1.getId(), "Петя Петров1");
        System.out.println("После обновления имени: " + userService.getUserById(user1.getId()).getUsername());

        userService.deleteUser(user2.getId());
        System.out.println("Пользователь с ID " + user2.getId() + " удален.");

        List<String> usernames = userService.getAllUsers().stream()
                .map(User::getUsername)
                .toList();

        System.out.println("Финальный список пользователей: " + usernames);

        context.close();

    }
}
