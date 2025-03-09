package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.JobDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {
    private final EmployerMapper employerMapper;

    public JobMapper(EmployerMapper employerMapper) {
        this.employerMapper = employerMapper;
    }

    public JobDTO toJobDTO(Job job) {
        if (job == null) return null;

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setEmployer(employerMapper.toEmployerDTO(job.getEmployer()));
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setJobType(job.getJobType());
        jobDTO.setPostedDate(job.getPostedDate());

        return jobDTO;
    }

    public Job toJobEntity(JobDTO jobDTO, Employer employer) {
        if (jobDTO == null) return null;

        Job job = new Job();
        job.setEmployer(employer);
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setJobType(jobDTO.getJobType());
        job.setPostedDate(java.time.LocalDateTime.now());

        return job;
    }

    public List<JobDTO> toJobDTOList(List<Job> jobs) {
        return jobs.stream()
                .map(this::toJobDTO)
                .collect(Collectors.toList());
    }
}
