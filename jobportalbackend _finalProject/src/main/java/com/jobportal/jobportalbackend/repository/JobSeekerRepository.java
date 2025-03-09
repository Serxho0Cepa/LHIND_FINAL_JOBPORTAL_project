package com.jobportal.jobportalbackend.repository;

import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    Optional<JobSeeker> findByUser(User user);

    @Query("SELECT js FROM JobSeeker js WHERE js.firstName LIKE %:name% OR js.lastName LIKE %:name%")
    Page<JobSeeker> findByNameContaining(@Param("name") String name, Pageable pageable);
}
