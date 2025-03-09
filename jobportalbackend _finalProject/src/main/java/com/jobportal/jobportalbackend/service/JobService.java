package com.jobportal.jobportalbackend.service;


import com.jobportal.jobportalbackend.mapper.JobMapper;
import com.jobportal.jobportalbackend.model.dto.JobDTO;
import com.jobportal.jobportalbackend.model.dto.JobSearchDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.Job;
import com.jobportal.jobportalbackend.repository.EmployerRepository;
import com.jobportal.jobportalbackend.repository.JobRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository,
                      EmployerRepository employerRepository,
                      JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.employerRepository = employerRepository;
        this.jobMapper = jobMapper;
    }

    @Transactional
    public JobDTO postJob(Long employerId, JobDTO jobDTO) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job job = jobMapper.toJobEntity(jobDTO, employer);
        job = jobRepository.save(job);

        return jobMapper.toJobDTO(job);
    }


    @Transactional(readOnly = true)
    public Page<JobDTO> searchJobs(JobSearchDTO searchDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Job> jobs = jobRepository.searchJobs(
                searchDTO.getTitle(),
                searchDTO.getLocation(),
                searchDTO.getMinSalary(),
                searchDTO.getJobType(),
                searchDTO.getEmployerName(),
                pageable
        );

        return jobs.map(jobMapper::toJobDTO);
    }


    @Transactional(readOnly = true)
    public Page<JobDTO> searchJobsWithFilters(String title, String location, Double minSalary, String jobType, String employerName, int page, int size) {

        JobSearchDTO searchDTO = new JobSearchDTO();
        searchDTO.setTitle(title);
        searchDTO.setLocation(location);
        searchDTO.setMinSalary(minSalary);
        searchDTO.setJobType(jobType);
        searchDTO.setEmployerName(employerName);

        return searchJobs(searchDTO, page, size);
    }

    @Transactional(readOnly = true)
    public Page<JobDTO> getEmployerJobs(Long employerId, int page, int size, String title, String location) {
        Employer employer = employerRepository.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<Job> jobs;
        if (title != null && location != null) {
            jobs = jobRepository.findByEmployerAndTitleContainingAndLocationContaining(employer, title, location, pageable);
        } else if (title != null) {
            jobs = jobRepository.findByEmployerAndTitleContaining(employer, title, pageable);
        } else if (location != null) {
            jobs = jobRepository.findByEmployerAndLocationContaining(employer, location, pageable);
        } else {
            jobs = jobRepository.findByEmployer(employer, pageable);
        }

        return jobs.map(jobMapper::toJobDTO);
    }


}
