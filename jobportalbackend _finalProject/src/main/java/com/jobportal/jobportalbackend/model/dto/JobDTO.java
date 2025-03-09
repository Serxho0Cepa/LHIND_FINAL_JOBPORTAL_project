package com.jobportal.jobportalbackend.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class JobDTO {
    private Long id;
    private EmployerDTO employer;
    private String title;
    private String description;
    private String location;
    private Double salary;
    private String jobType;
    private LocalDateTime postedDate;
    private List<JobApplicationDTO> applications;
    private List<JobReviewDTO> reviews;


    public JobDTO() {}

    public JobDTO(Long id, EmployerDTO employer, String title, String description, String location) {
        this.id = id;
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.location = location;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmployerDTO getEmployer() { return employer; }
    public void setEmployer(EmployerDTO employer) { this.employer = employer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }

    public List<JobApplicationDTO> getApplications() { return applications; }
    public void setApplications(List<JobApplicationDTO> applications) { this.applications = applications; }

    public List<JobReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<JobReviewDTO> reviews) { this.reviews = reviews; }
}
