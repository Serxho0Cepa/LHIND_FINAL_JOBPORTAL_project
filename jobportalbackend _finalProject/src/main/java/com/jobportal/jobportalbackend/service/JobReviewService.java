package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.mapper.JobReviewMapper;
import com.jobportal.jobportalbackend.model.dto.JobReviewDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobReview;
import com.jobportal.jobportalbackend.repository.EmployerRepository;
import com.jobportal.jobportalbackend.repository.JobRepository;
import com.jobportal.jobportalbackend.repository.JobReviewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobReviewService {
    private final JobReviewRepository jobReviewRepository;
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final JobReviewMapper jobReviewMapper;

    public JobReviewService(JobReviewRepository jobReviewRepository,
                            JobRepository jobRepository,
                            EmployerRepository employerRepository,
                            JobReviewMapper jobReviewMapper) {
        this.jobReviewRepository = jobReviewRepository;
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
        this.jobReviewMapper = jobReviewMapper;
    }

      //Without authorization
//    @Transactional
//    public JobReviewDTO addJobReview(Long employerId, Long jobId, JobReviewDTO reviewDTO) {
//
//        Employer employer = employerRepository.findById(employerId)
//                .orElseThrow(() -> new RuntimeException("Employer not found"));
//
//        Job job = jobRepository.findById(jobId)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        if (!job.getEmployer().equals(employer)) {
//            throw new RuntimeException("You are not authorized to add a review for this job");
//        }
//
//        JobReview jobReview = jobReviewMapper.toJobReviewEntity(reviewDTO, job, employer);
//
//        jobReview = jobReviewRepository.save(jobReview);
//
//        return jobReviewMapper.toJobReviewDTO(jobReview);
//    }


    @Transactional
    public JobReviewDTO addJobReview(Long jobId, JobReviewDTO reviewDTO, Authentication authentication) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Employer employer = job.getEmployer();
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Employer authenticatedEmployer = employerRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Authenticated employer not found"));

        if (!authenticatedEmployer.equals(employer)) {
            throw new RuntimeException("You are not authorized to add a review for this job");
        }

        JobReview jobReview = jobReviewMapper.toJobReviewEntity(reviewDTO, job, employer);
        jobReview = jobReviewRepository.save(jobReview);

        return jobReviewMapper.toJobReviewDTO(jobReview);
    }


    @Transactional(readOnly = true)
    public Page<JobReviewDTO> getJobReviews(Long jobId, Integer minRating, int page, int size) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<JobReview> reviews = jobReviewRepository.findByJobAndMinRating(
                job,
                minRating,
                pageable
        );

        return reviews.map(jobReviewMapper::toJobReviewDTO);
    }


}
