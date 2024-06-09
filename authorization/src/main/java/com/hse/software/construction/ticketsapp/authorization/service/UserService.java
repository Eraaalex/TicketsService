package com.hse.software.construction.ticketsapp.authorization.service;

import com.hse.software.construction.ticketsapp.authorization.dto.AuthentificationRequest;
import com.hse.software.construction.ticketsapp.authorization.exception.UserAlreadyExistsException;
import com.hse.software.construction.ticketsapp.authorization.model.User;
import com.hse.software.construction.ticketsapp.authorization.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@AllArgsConstructor
@Service
@Slf4j
public class UserService {
    private JwtService jwtService;
    private UserRepository userRepository;
    public ResponseEntity<String> login(AuthentificationRequest authenticationRequest) {
        Optional<User> userDetails = findByUsername(authenticationRequest.getUsername());
        if (userDetails.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String jwt = jwtService.generateToken(userDetails.get());
        return ResponseEntity.ok(jwt);
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public ResponseEntity<?> getUserData(String token) {
        String username = jwtService.getUsernameFromToken(token);
        Optional<User> userDetails = findByUsername(username);
        if (userDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        return ResponseEntity.ok(userDetails.get());
    }

    @Transactional
    public HttpEntity<String> createNewUser(User user) throws AuthenticationException {
        User oldUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (oldUser != null) {
            throw new UserAlreadyExistsException(String.format("User '%s' already exists", user.getUsername()));
        }
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully");
    }

}
