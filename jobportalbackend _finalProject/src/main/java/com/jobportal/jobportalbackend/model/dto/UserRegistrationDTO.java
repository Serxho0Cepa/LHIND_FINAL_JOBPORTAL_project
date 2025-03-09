package com.jobportal.jobportalbackend.model.dto;

import com.jobportal.jobportalbackend.model.enums.UserRole;


public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private UserRole role;


    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
