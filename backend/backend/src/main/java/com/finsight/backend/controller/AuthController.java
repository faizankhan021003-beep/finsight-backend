package com.finsight.backend.controller;

import com.finsight.backend.dto.LoginRequest;
import com.finsight.backend.dto.RegisterRequest;
import com.finsight.backend.service.AuthService;
import com.finsight.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService,
                          AuthService authService) {

        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}