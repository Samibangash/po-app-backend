package com.poapp.dto;

public class UserDTO {
    private Integer id;
    private String username;
    private String password; // Add password to the DTO
    private String role;
    private String role_name;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getRoleName() {
        return role_name;
    }

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }
}
