package com.example.finalfront.controller;

import com.example.finalfront.dto.LoginRequest;
import com.example.finalfront.dto.RegisterRequest;
import com.example.finalfront.models.User;
import com.example.finalfront.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "users/details";
    }

    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "users/update";
    }

    @GetMapping("/create")
    public String createUserForm() {
        return "users/create";
    }

    @PostMapping
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "users/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(RegisterRequest registerRequest) {
        log.info(registerRequest.getLogin());
        String resp = userService.registerUser(registerRequest);
        if (Objects.equals(resp, "Пользователь успешно зарегистрирован")) {
            return "redirect:/users/login";
        }

        return "redirect:/users/register";
    }

    @PostMapping("/login")
    public String loginUser(LoginRequest loginRequest) {
        String resp = userService.loginUser(loginRequest);

        if (resp.contains("Ошибка аутентификации:")) {
            return "users/login";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }
}
