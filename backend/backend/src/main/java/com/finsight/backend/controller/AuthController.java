package com.finsight.backend.controller;

import com.finsight.backend.dto.LoginRequest;
import com.finsight.backend.dto.RegisterRequest;
import com.finsight.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }
    @PostMapping("/login")
public String loginUser(
        @RequestBody LoginRequest request) {

    return userService.loginUser(request);
}
}