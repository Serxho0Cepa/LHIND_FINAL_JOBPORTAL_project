package com.jobportal.jobportalbackend.controller;

import com.jobportal.jobportalbackend.model.dto.JobApplicationDTO;
import com.jobportal.jobportalbackend.model.dto.JobDTO;
import com.jobportal.jobportalbackend.model.dto.JobSearchDTO;
import com.jobportal.jobportalbackend.model.dto.JobSeekerDTO;
import com.jobportal.jobportalbackend.model.enums.ApplicationStatus;
import com.jobportal.jobportalbackend.service.JobApplicationService;
import com.jobportal.jobportalbackend.service.JobSeekerService;
import com.jobportal.jobportalbackend.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/job-seekers")
public class JobSeekerController {
    private final JobSeekerService jobSeekerService;
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;

    public JobSeekerController(JobSeekerService jobSeekerService,
                               JobService jobService,
                               JobApplicationService jobApplicationService) {
        this.jobSeekerService = jobSeekerService;
        this.jobService = jobService;
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/{userId}/profile")
    public ResponseEntity<JobSeekerDTO> createJobSeekerProfile(
            @PathVariable Long userId,
            @RequestBody JobSeekerDTO jobSeekerDTO
    ) {
        JobSeekerDTO createdProfile = jobSeekerService.createJobSeekerProfile(userId, jobSeekerDTO);
        return ResponseEntity.ok(createdProfile);
    }

    @PostMapping("/{jobSeekerId}/resume")
    public ResponseEntity<JobSeekerDTO> uploadResume(
            @PathVariable Long jobSeekerId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        JobSeekerDTO updatedJobSeeker = jobSeekerService.uploadResume(jobSeekerId, file);
        return ResponseEntity.ok(updatedJobSeeker);
    }

    @PostMapping("/{jobSeekerId}/apply/{jobId}")
    public ResponseEntity<JobApplicationDTO> applyForJob(
            @PathVariable Long jobSeekerId,
            @PathVariable Long jobId,
            @RequestBody JobApplicationDTO applicationDTO
    ) {
        JobApplicationDTO appliedJob = jobApplicationService.applyForJob(jobSeekerId, jobId, applicationDTO);
        return ResponseEntity.ok(appliedJob);
    }

    @GetMapping("/jobs")
    public ResponseEntity<Page<JobDTO>> searchJobs(
            @RequestBody JobSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<JobDTO> jobs = jobService.searchJobs(searchDTO, page, size);
        return ResponseEntity.ok(jobs);
    }


    @GetMapping("/{jobSeekerId}/applications")
    public ResponseEntity<Page<JobApplicationDTO>> getJobSeekerApplications(
            @PathVariable Long jobSeekerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) ApplicationStatus status
    ) {
        Page<JobApplicationDTO> applications = jobApplicationService.getApplicationsByJobSeeker(
                jobSeekerId, page, size, jobTitle, status);
        return ResponseEntity.ok(applications);
    }


    @GetMapping("/{jobSeekerId}/jobs")
    public ResponseEntity<Page<JobDTO>> getAllJobs(
            @PathVariable Long jobSeekerId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String employerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<JobDTO> jobs = jobService.searchJobsWithFilters(title, location, minSalary, jobType, employerName, page, size);
        return ResponseEntity.ok(jobs);
    }

}

