package com.hse.software.construction.ticketsapp.authorization.controller;


import com.hse.software.construction.ticketsapp.authorization.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.getUserData(token);
    }

    @GetMapping("/home")
    public String init() {
        return "Home page";
    }
}
