package com.jobportal.jobportalbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @NotBlank(message = "Job title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Job description is required")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Location is required")
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "posted_date")
    private LocalDateTime postedDate;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobReview> reviews;


    public Job() {
        this.postedDate = LocalDateTime.now();
    }

    public Job(Employer employer, String title, String description, String location) {
        this.employer = employer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.postedDate = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employer getEmployer() { return employer; }
    public void setEmployer(Employer employer) { this.employer = employer; }

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

    public List<JobApplication> getApplications() { return applications; }
    public void setApplications(List<JobApplication> applications) { this.applications = applications; }

    public List<JobReview> getReviews() { return reviews; }
    public void setReviews(List<JobReview> reviews) { this.reviews = reviews; }
}
