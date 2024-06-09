package com.hse.software.construction.ticketsapp.authorization.dto;


public class AuthentificationRequest {
    private String username;
    private final String password;

    public AuthentificationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}