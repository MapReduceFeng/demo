package com.example.demo.system.entity;

import java.util.List;


public class TokenRoles {
    private String token;
    private List<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public TokenRoles() {
    }

    public TokenRoles(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}
