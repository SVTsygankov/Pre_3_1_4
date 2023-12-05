package ru.SVTsygankov.security.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SVTsygankov.security.model.User;
import ru.SVTsygankov.security.repository.UsersRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UsersRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() { return userRepository.findAllUniqueUsers(); }

    @Override
    public User findUserById(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new UsernameNotFoundException("Пользователь с ID="+id+" не найден");
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("Пользователя с email = " + email + " не найден");
        return  user.get();
        }

    @Transactional
    @Override
    public void updateUser(User updateUser, Long id) {
        updateUser.setId(id);
        if (!updateUser.getPassword().equals(userRepository.findById(id).get().getPassword())) {
            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        userRepository.save(updateUser);
    }
}
