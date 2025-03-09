package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.mapper.JobApplicationMapper;
import com.jobportal.jobportalbackend.model.dto.ApplicationFilterDTO;
import com.jobportal.jobportalbackend.model.dto.JobApplicationDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobApplication;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;
import com.jobportal.jobportalbackend.repository.EmployerRepository;
import com.jobportal.jobportalbackend.repository.JobApplicationRepository;
import com.jobportal.jobportalbackend.repository.JobRepository;
import com.jobportal.jobportalbackend.repository.JobSeekerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobApplicationMapper jobApplicationMapper;
    private final EmployerRepository employerRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository,
                                 JobRepository jobRepository,
                                 JobSeekerRepository jobSeekerRepository,
                                 JobApplicationMapper jobApplicationMapper,
                                 EmployerRepository employerRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobApplicationMapper = jobApplicationMapper;
        this.employerRepository = employerRepository;
    }

    @Transactional
    public JobApplicationDTO applyForJob(Long jobSeekerId, Long jobId, JobApplicationDTO applicationDTO) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Ensure status is set to APPLIED if not provided
        if (applicationDTO.getStatus() == null) {
            applicationDTO.setStatus(ApplicationStatus.APPLIED);
        }

        JobApplication jobApplication = jobApplicationMapper.toJobApplicationEntity(
                applicationDTO, jobSeeker, job
        );

        jobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toJobApplicationDTO(jobApplication);
    }


    @Transactional(readOnly = true)
    public Page<JobApplicationDTO> getJobApplications(Long jobId, ApplicationStatus status, int page, int size) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplication> applications;

        if (status != null) {
            applications = jobApplicationRepository.findByJobAndStatus(job, status, pageable);
        } else {
            applications = jobApplicationRepository.findByJob(job, pageable);
        }

        return applications.map(jobApplicationMapper::toJobApplicationDTO);
    }


    @Transactional
    public JobApplicationDTO updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {

        JobApplication jobApplication = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Job Application not found"));

        Job job = jobApplication.getJob();
        Employer employer = job.getEmployer();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Employer authenticatedEmployer = employerRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Authenticated employer not found"));

        if (!authenticatedEmployer.equals(employer)) {
            throw new RuntimeException("You are not authorized to update this application status.");

        }

        jobApplication.setStatus(newStatus);
        jobApplication = jobApplicationRepository.save(jobApplication);

        return jobApplicationMapper.toJobApplicationDTO(jobApplication);
    }



    @Transactional(readOnly = true)
    public Page<JobApplicationDTO> getApplicationsByJobSeeker(Long jobSeekerId, int page, int size, String jobTitle, ApplicationStatus status) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<JobApplication> applications;

        if (jobTitle != null && status != null) {
            applications = jobApplicationRepository.findByJobSeekerAndJobTitleAndStatus(jobSeeker, jobTitle, status, pageable);
        } else if (jobTitle != null) {
            applications = jobApplicationRepository.findByJobSeekerAndJobTitle(jobSeeker, jobTitle, pageable);
        } else if (status != null) {
            applications = jobApplicationRepository.findByJobSeekerAndStatus(jobSeeker, status, pageable);
        } else {
            applications = jobApplicationRepository.findByJobSeeker(jobSeeker, pageable);
        }
        return applications.map(jobApplicationMapper::toJobApplicationDTO);
    }


}
