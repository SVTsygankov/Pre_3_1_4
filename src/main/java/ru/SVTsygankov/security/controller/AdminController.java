package ru.SVTsygankov.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.SVTsygankov.security.service.RoleService;
import ru.SVTsygankov.security.service.UserService;
import ru.SVTsygankov.security.model.User;
import ru.SVTsygankov.security.model.Role;
import ru.SVTsygankov.security.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/adminShowAll";
    }

    @GetMapping("/new")
    public ModelAndView newUser() {
        User user = new User();
// ModelAndView ModelAndView — это класс, который используется в Spring MVC для передачи модели и имени представления
// из контроллера в метод DispatcherServlet. Он содержит две основные части: объект модели данных и имя представления,
// в котором модель будет отображаться.
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);

        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);

        return mav;
    }

    @PostMapping("/new")
        public ModelAndView create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("admin/new");
        mav.addObject("user", user);
        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) { return mav; }

        userService.saveUser(user);
        mav.setViewName("redirect:/admin");
        return mav;
    }

    @GetMapping ("/{id}/edit")
        public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/edit");
        mav.addObject("user", userService.findUserById(id));
        List<Role> roles = roleService.findAll();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.updateUser(user, user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
