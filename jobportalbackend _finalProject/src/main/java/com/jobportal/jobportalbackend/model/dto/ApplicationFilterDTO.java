package com.jobportal.jobportalbackend.model.dto;

import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationFilterDTO {
    private ApplicationStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
