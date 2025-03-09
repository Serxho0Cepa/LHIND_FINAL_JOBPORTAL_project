package com.jobportal.jobportalbackend.repository;

import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobApplication;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByJob(Job job, Pageable pageable);
    Page<JobApplication> findByJobSeeker(JobSeeker jobSeeker, Pageable pageable);

    @Query("SELECT ja FROM JobApplication ja WHERE " +
            "ja.job = :job AND " +
            "(:status IS NULL OR ja.status = :status)")
    Page<JobApplication> findByJobAndStatus(
            @Param("job") Job job,
            @Param("status") ApplicationStatus status,
            Pageable pageable
    );

    Page<JobApplication> findByJobSeekerAndJobTitle(JobSeeker jobSeeker, String jobTitle, Pageable pageable);

    Page<JobApplication> findByJobSeekerAndStatus(JobSeeker jobSeeker, ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByJobSeekerAndJobTitleAndStatus(JobSeeker jobSeeker, String jobTitle, ApplicationStatus status, Pageable pageable);
}
