package ru.SVTsygankov.security.service;

import ru.SVTsygankov.security.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User findByUsername(String email);

    boolean deleteUser(Long id);

    boolean saveUser(User user);

    void updateUser(User user, Long id);
}
