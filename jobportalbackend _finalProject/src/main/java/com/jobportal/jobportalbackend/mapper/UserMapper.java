package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.UserDTO;
import com.jobportal.jobportalbackend.model.dto.UserRegistrationDTO;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setEnabled(user.isEnabled());

        return userDTO;
    }

    public User toUserEntity(UserRegistrationDTO registrationDTO) {
        if (registrationDTO == null) return null;

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        user.setRole(registrationDTO.getRole());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setEnabled(true);

        return user;
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }
}
