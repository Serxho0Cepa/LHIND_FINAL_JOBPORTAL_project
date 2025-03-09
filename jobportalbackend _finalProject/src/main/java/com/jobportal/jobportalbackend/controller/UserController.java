package com.jobportal.jobportalbackend.controller;

import com.jobportal.jobportalbackend.model.dto.UserDTO;
import com.jobportal.jobportalbackend.model.dto.UserRegistrationDTO;
import com.jobportal.jobportalbackend.model.enums.UserRole;
import com.jobportal.jobportalbackend.service.EmailService;
import com.jobportal.jobportalbackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO createdUser = userService.registerUser(registrationDTO);

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UserRole role
    ) {
        Page<UserDTO> users = userService.getAllUsers(page, size, role);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
