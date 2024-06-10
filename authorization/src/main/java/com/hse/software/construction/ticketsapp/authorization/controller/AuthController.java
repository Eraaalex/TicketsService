package com.hse.software.construction.ticketsapp.authorization.controller;

import com.hse.software.construction.ticketsapp.authorization.dto.AuthentificationRequest;
import com.hse.software.construction.ticketsapp.authorization.exception.UserAlreadyExistsException;
import com.hse.software.construction.ticketsapp.authorization.model.User;
import com.hse.software.construction.ticketsapp.authorization.service.JwtService;
import com.hse.software.construction.ticketsapp.authorization.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@Slf4j

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
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

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthentificationRequest request) {
        try {
            return userService.login(request);
        } catch (UserAlreadyExistsException e) {
            log.info("Failed to login the user: user with username {} already exists", request.getUsername());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("Error occurred while logging in user", e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            log.info("Trying get user data");
            return userService.getUserData(token);
        } catch (JwtException e) {
            log.error("Invalid ex:" + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token");
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JWT token");
        } catch (Exception e) {
            log.error("Invalid ex: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }


}
