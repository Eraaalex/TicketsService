package com.hse.software.construction.ticketsapp.authorization.controller;


import com.hse.software.construction.ticketsapp.authorization.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class MainController {
    private final UserService userService;


    @GetMapping("/home")
    public String init() {
        return "Home page";
    }

}
