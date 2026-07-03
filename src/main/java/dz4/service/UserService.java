package dz4.service;

import dz4.dao.UserDao;
import dz4.entity.User;
import dz4.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String username) {
        User user = new User(username);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id " + id + " не найден"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByUserName(String userName) {
        return userRepository.findByUsernameCustom(userName)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с именем " + userName + " не найден"));
    }

    @Transactional
    public void updateUser(Long id, String newUsername) {
        User user = getUserById(id);
        user.setUsername(newUsername);
        // Метод save() выполнит UPDATE, так как у объекта user уже заполнен ID
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Пользователь с id " + id + " не существует");
        }
        userRepository.deleteById(id);
    }
}