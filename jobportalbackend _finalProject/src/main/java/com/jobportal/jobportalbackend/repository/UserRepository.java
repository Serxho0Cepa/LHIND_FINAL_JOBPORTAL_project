package com.jobportal.jobportalbackend.repository;


import com.jobportal.jobportalbackend.model.entity.User;
import com.jobportal.jobportalbackend.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findByRole(UserRole role, Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
}
