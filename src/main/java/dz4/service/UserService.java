package dz4.service;

import dz4.dao.UserDao;
import dz4.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userDao.save(user);
    }

    public User getUserById(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id " + id + " не найден"));
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void updateUser(Long id, String newUsername) {
        User user = getUserById(id);
        user.setUsername(newUsername);
        userDao.update(user);
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
}