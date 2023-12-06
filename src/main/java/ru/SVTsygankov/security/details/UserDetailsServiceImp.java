package ru.SVTsygankov.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SVTsygankov.security.model.User;
import ru.SVTsygankov.security.service.UserServiceImp;
@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private UserServiceImp userService;

    public UserDetailsServiceImp(UserServiceImp userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' не найден", username));
        }
        return user;
    }
}
