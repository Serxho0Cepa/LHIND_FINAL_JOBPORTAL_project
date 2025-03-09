package com.jobportal.jobportalbackend.repository;

import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByUser(User user);

    Optional<Employer> findByUser_Username(String username);

    @Query("SELECT e FROM Employer e WHERE e.companyName LIKE %:companyName%")
    Page<Employer> findByCompanyNameContaining(@Param("companyName") String companyName, Pageable pageable);
}
