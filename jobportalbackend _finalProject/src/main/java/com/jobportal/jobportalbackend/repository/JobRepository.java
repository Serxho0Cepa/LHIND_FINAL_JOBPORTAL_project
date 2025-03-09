package com.jobportal.jobportalbackend.repository;

import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByEmployer(Employer employer, Pageable pageable);

    Page<Job> findByEmployerAndTitleContaining(Employer employer, String title, Pageable pageable);

    Page<Job> findByEmployerAndLocationContaining(Employer employer, String location, Pageable pageable);

    Page<Job> findByEmployerAndTitleContainingAndLocationContaining(Employer employer, String title, String location, Pageable pageable);


    @Query("SELECT j FROM Job j JOIN j.employer e WHERE " +
            "(:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:minSalary IS NULL OR j.salary >= :minSalary) AND " +
            "(:jobType IS NULL OR LOWER(j.jobType) LIKE LOWER(CONCAT('%', :jobType, '%'))) AND " +
            "(:employerName IS NULL OR LOWER(e.companyName) LIKE LOWER(CONCAT('%', :employerName, '%')))")
    Page<Job> searchJobs(
            @Param("title") String title,
            @Param("location") String location,
            @Param("minSalary") Double minSalary,
            @Param("jobType") String jobType,
            @Param("employerName") String employerName,
            Pageable pageable
    );

}