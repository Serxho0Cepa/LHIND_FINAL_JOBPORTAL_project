package com.jobportal.jobportalbackend.controller;


import com.jobportal.jobportalbackend.model.dto.*;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;
import com.jobportal.jobportalbackend.service.EmployerService;
import com.jobportal.jobportalbackend.service.JobApplicationService;
import com.jobportal.jobportalbackend.service.JobReviewService;
import com.jobportal.jobportalbackend.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;


@RestController
@RequestMapping("/api/employers")
public class EmployerController {
    private final EmployerService employerService;
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final JobReviewService jobReviewService;

    public EmployerController(EmployerService employerService,
                              JobService jobService,
                              JobApplicationService jobApplicationService,
                              JobReviewService jobReviewService) {
        this.employerService = employerService;
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
        this.jobReviewService = jobReviewService;
    }

    @PostMapping("/{userId}/profile")
    public ResponseEntity<EmployerDTO> createEmployerProfile(
            @PathVariable Long userId,
            @RequestBody EmployerDTO employerDTO
    ) {
        EmployerDTO createdProfile = employerService.createEmployerProfile(userId, employerDTO);
        return ResponseEntity.ok(createdProfile);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployerDTO>> searchEmployers(
            @RequestParam String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<EmployerDTO> employers = employerService.searchEmployers(companyName, page, size);
        return ResponseEntity.ok(employers);
    }

    @PostMapping("/{employerId}/jobs")
    public ResponseEntity<JobDTO> postJob(
            @PathVariable Long employerId,
            @RequestBody JobDTO jobDTO
    ) {
        JobDTO postedJob = jobService.postJob(employerId, jobDTO);
        return ResponseEntity.ok(postedJob);
    }

    @GetMapping("/{employerId}/jobs")
    public ResponseEntity<Page<JobDTO>> getEmployerJobs(
            @PathVariable Long employerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location
    ) {
        Page<JobDTO> jobs = jobService.getEmployerJobs(employerId, page, size, title, location);
        return ResponseEntity.ok(jobs);
    }


    @GetMapping("/{employerId}/jobs/{jobId}/applications")
    public ResponseEntity<Page<JobApplicationDTO>> getJobApplications(
            @PathVariable Long jobId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ApplicationStatus status
    ) {
        Page<JobApplicationDTO> applications = jobApplicationService.getJobApplications(jobId, status, page, size);
        return ResponseEntity.ok(applications);
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<JobApplicationDTO> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus newStatus
    ) {
        JobApplicationDTO updatedApplication = jobApplicationService.updateApplicationStatus(applicationId, newStatus);
        return ResponseEntity.ok(updatedApplication);
    }

    //Without authorization, but withs employer Id on url
//    @PostMapping("/{employerId}/jobs/{jobId}/reviews")
//    public ResponseEntity<JobReviewDTO> addJobReview(
//            @PathVariable Long employerId,
//            @PathVariable Long jobId,
//            @RequestBody JobReviewDTO reviewDTO
//    ) {
//        JobReviewDTO addedReview = jobReviewService.addJobReview(employerId, jobId, reviewDTO);
//        return ResponseEntity.ok(addedReview);
//    }

    @PostMapping("/jobs/{jobId}/reviews")
    public ResponseEntity<JobReviewDTO> addJobReview(
            @PathVariable Long jobId,
            @RequestBody JobReviewDTO reviewDTO,
            @AuthenticationPrincipal Authentication authentication) {

        JobReviewDTO addedReview = jobReviewService.addJobReview(jobId, reviewDTO, authentication);
        return ResponseEntity.ok(addedReview);
    }

}

