package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.JobReviewDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobReview;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobReviewMapper {
    private final JobMapper jobMapper;
    private final EmployerMapper employerMapper;

    public JobReviewMapper(JobMapper jobMapper, EmployerMapper employerMapper) {
        this.jobMapper = jobMapper;
        this.employerMapper = employerMapper;
    }

    public JobReviewDTO toJobReviewDTO(JobReview jobReview) {
        if (jobReview == null) return null;

        JobReviewDTO jobReviewDTO = new JobReviewDTO();
        jobReviewDTO.setId(jobReview.getId());
        jobReviewDTO.setJob(jobMapper.toJobDTO(jobReview.getJob()));
        jobReviewDTO.setEmployer(employerMapper.toEmployerDTO(jobReview.getEmployer()));
        jobReviewDTO.setRating(jobReview.getRating());
        jobReviewDTO.setReviewText(jobReview.getReviewText());
        jobReviewDTO.setReviewDate(jobReview.getReviewDate());

        return jobReviewDTO;
    }

    public JobReview toJobReviewEntity(JobReviewDTO jobReviewDTO,
                                       Job job,
                                       Employer employer) {
        if (jobReviewDTO == null) return null;

        JobReview jobReview = new JobReview();
        jobReview.setJob(job);
        jobReview.setEmployer(employer);
        jobReview.setRating(jobReviewDTO.getRating());
        jobReview.setReviewText(jobReviewDTO.getReviewText());
        jobReview.setReviewDate(java.time.LocalDateTime.now());

        return jobReview;
    }

    public List<JobReviewDTO> toJobReviewDTOList(List<JobReview> jobReviews) {
        return jobReviews.stream()
                .map(this::toJobReviewDTO)
                .collect(Collectors.toList());
    }
}
