package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.mapper.UserMapper;
import com.jobportal.jobportalbackend.model.dto.UserDTO;
import com.jobportal.jobportalbackend.model.dto.UserRegistrationDTO;
import com.jobportal.jobportalbackend.model.entity.User;
import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;
import com.jobportal.jobportalbackend.model.enums.UserRole;
import com.jobportal.jobportalbackend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {

        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        User user = userMapper.toUserEntity(registrationDTO);
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        user.setVerificationCode(uuidString);
        user.setVerified(false);

        user = userRepository.save(user);

        System.out.println("User Role: " + user.getRole());

        if (user.getRole().equals(UserRole.ADMIN)) {
            emailService.sendVerificationEmail(uuidString,"serxho.cepa@gmail.com");
        } else {
            emailService.sendVerificationEmail(uuidString, user.getEmail());
        }

        return userMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(int page, int size, UserRole role) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = role != null
                ? userRepository.findByRole(role, pageable)
                : userRepository.findAll(pageable);

        return users.map(userMapper::toUserDTO);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean verifyUser(String verificationToken) {
        Optional<User> user = userRepository.findByVerificationCode(verificationToken);
        if (user.isPresent()) {
            user.get().setVerificationCode(null);
            user.get().setVerified(true);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

}
