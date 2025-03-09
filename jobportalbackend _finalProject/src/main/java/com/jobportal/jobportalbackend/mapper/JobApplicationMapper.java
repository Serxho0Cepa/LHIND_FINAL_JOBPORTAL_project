package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.JobApplicationDTO;
import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.model.entity.JobApplication;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobApplicationMapper {
    private final JobSeekerMapper jobSeekerMapper;
    private final JobMapper jobMapper;

    public JobApplicationMapper(JobSeekerMapper jobSeekerMapper, JobMapper jobMapper) {
        this.jobSeekerMapper = jobSeekerMapper;
        this.jobMapper = jobMapper;
    }

    public JobApplicationDTO toJobApplicationDTO(JobApplication jobApplication) {
        if (jobApplication == null) return null;

        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setId(jobApplication.getId());
        jobApplicationDTO.setJobSeeker(jobSeekerMapper.toJobSeekerDTO(jobApplication.getJobSeeker()));
        jobApplicationDTO.setJob(jobMapper.toJobDTO(jobApplication.getJob()));
        jobApplicationDTO.setApplicationDate(jobApplication.getApplicationDate());
        jobApplicationDTO.setStatus(jobApplication.getStatus());
        jobApplicationDTO.setCoverLetter(jobApplication.getCoverLetter());

        return jobApplicationDTO;
    }

    public JobApplication toJobApplicationEntity(JobApplicationDTO jobApplicationDTO,
                                                 JobSeeker jobSeeker,
                                                 Job job) {
        if (jobApplicationDTO == null) return null;

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobSeeker(jobSeeker);
        jobApplication.setJob(job);
        jobApplication.setApplicationDate(java.time.LocalDateTime.now());
        jobApplication.setStatus(jobApplicationDTO.getStatus());
        jobApplication.setCoverLetter(jobApplicationDTO.getCoverLetter());

        return jobApplication;
    }

    public List<JobApplicationDTO> toJobApplicationDTOList(List<JobApplication> jobApplications) {
        return jobApplications.stream()
                .map(this::toJobApplicationDTO)
                .collect(Collectors.toList());
    }
}