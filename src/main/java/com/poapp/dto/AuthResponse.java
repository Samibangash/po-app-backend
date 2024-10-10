package com.poapp.dto;

import com.poapp.model.User;

public class AuthResponse {
    private String jwt;
    private User user; // Add the User object

    public AuthResponse(String jwt,  User user) {
        this.jwt = jwt;
        this.user = user; // Initialize user
    }

    // Getters and setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    // public UserDetails getUserDetails() {
    //     return userDetails;
    // }

    // public void setUserDetails(UserDetails userDetails) {
    //     this.userDetails = userDetails;
    // }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
