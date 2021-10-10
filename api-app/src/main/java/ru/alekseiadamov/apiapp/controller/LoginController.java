package ru.alekseiadamov.apiapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/login")
@RestController("apiLoginController")
public class LoginController {

    @GetMapping
    public User login(Authentication auth) {
        return (User) auth.getPrincipal();
    }
}
