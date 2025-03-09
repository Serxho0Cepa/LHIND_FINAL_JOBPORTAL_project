package com.jobportal.jobportalbackend.controller;

import com.jobportal.jobportalbackend.model.dto.JobReviewDTO;
import com.jobportal.jobportalbackend.service.JobReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobReviewController {
    private final JobReviewService jobReviewService;

    public JobReviewController(JobReviewService jobReviewService) {
        this.jobReviewService = jobReviewService;
    }


    @GetMapping("/{jobId}/reviews")
    public ResponseEntity<Page<JobReviewDTO>> getJobReviews(
            @PathVariable Long jobId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<JobReviewDTO> reviews = jobReviewService.getJobReviews(jobId, minRating, page, size);
        return ResponseEntity.ok(reviews);
    }

}
