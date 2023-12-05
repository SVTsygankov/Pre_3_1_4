/*
package ru.SVTsygankov.security.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.SVTsygankov.security.service.UserService;
import ru.SVTsygankov.security.model.User;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        System.out.println(" Пользователь в валидаторе = " + user);
        if(userService.findUserByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Этот email уже занят. Выберете другой");
        }
    }
}
*/