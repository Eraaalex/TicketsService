package com.hse.software.construction.ticketsapp.authorization.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthentificationRequest {
    private String username;
    private final String password;
}