package ru.alekseiadamov.apiapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @GetMapping("/all")
    public Authentication findAll(Authentication auth) {
        return auth;
    }
}
