package com.jobportal.jobportalbackend.model.dto;

import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class JobApplicationDTO {
    private Long id;
    private JobSeekerDTO jobSeeker;
    private JobDTO job;
    private LocalDateTime applicationDate;
    private ApplicationStatus status;
    private String coverLetter;


    public JobApplicationDTO() {}

    public JobApplicationDTO(Long id, JobSeekerDTO jobSeeker, JobDTO job) {
        this.id = id;
        this.jobSeeker = jobSeeker;
        this.job = job;
        this.applicationDate = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JobSeekerDTO getJobSeeker() { return jobSeeker; }
    public void setJobSeeker(JobSeekerDTO jobSeeker) { this.jobSeeker = jobSeeker; }

    public JobDTO getJob() { return job; }
    public void setJob(JobDTO job) { this.job = job; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }
}
