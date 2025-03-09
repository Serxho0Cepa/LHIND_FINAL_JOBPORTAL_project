package com.jobportal.jobportalbackend.model.dto;

import java.time.LocalDateTime;

public class JobReviewDTO {
    private Long id;
    private JobDTO job;
    private EmployerDTO employer;
    private Integer rating;
    private String reviewText;
    private LocalDateTime reviewDate;


    public JobReviewDTO() {}

    public JobReviewDTO(Long id, JobDTO job, EmployerDTO employer, Integer rating) {
        this.id = id;
        this.job = job;
        this.employer = employer;
        this.rating = rating;
        this.reviewDate = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JobDTO getJob() { return job; }
    public void setJob(JobDTO job) { this.job = job; }

    public EmployerDTO getEmployer() { return employer; }
    public void setEmployer(EmployerDTO employer) { this.employer = employer; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }
}
