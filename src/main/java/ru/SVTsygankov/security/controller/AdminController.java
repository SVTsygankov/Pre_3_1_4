package ru.SVTsygankov.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.SVTsygankov.security.service.RoleService;
import ru.SVTsygankov.security.service.UserService;
import ru.SVTsygankov.security.model.User;
import ru.SVTsygankov.security.model.Role;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAll(@ModelAttribute("user") User user, Principal principal, Model model) {
        User authenticatedUser = userService.findUserByEmail(principal.getName()).get(); //User который аутентифицировался
        System.out.println("User который аутентифицировался = " + authenticatedUser);
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("rolesAuthenticatedUser", authenticatedUser.getRoles());
        model.addAttribute("users", userService.findAll());
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/adminShowAll";
    }

    @PostMapping("")
    public String saveUser(@ModelAttribute("user")  User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user")  User user) {
        userService.updateUser(user, user.getId());
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
