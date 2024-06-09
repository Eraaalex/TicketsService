package com.hse.software.construction.ticketsapp.authorization.controller;

import com.hse.software.construction.ticketsapp.authorization.dto.AuthentificationRequest;
import com.hse.software.construction.ticketsapp.authorization.exception.UserAlreadyExistsException;
import com.hse.software.construction.ticketsapp.authorization.model.User;
import com.hse.software.construction.ticketsapp.authorization.service.JwtService;
import com.hse.software.construction.ticketsapp.authorization.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


//@Slf4j

@RestController
@AllArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/register")
    public HttpEntity<String> registerUser(@RequestBody User user) {
        try {
            return userService.createNewUser(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Failed to register the user: user with email " + user.getEmail() + " already exists");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register the user: user with email " + user.getEmail() + "via exception:" + e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        try {
            boolean isValid = jwtService.isExpired(token);
            return new ResponseEntity<>(isValid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthentificationRequest request) {
        try {
            return userService.login(request);
        } catch (UserAlreadyExistsException e) {
            logger.info("Failed to login the user: user with username {} already exists", request.getUsername());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Error occurred while logging in user", e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
