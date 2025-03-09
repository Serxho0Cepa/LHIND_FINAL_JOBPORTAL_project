package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.JobSeekerDTO;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobSeekerMapper {
    private final UserMapper userMapper;

    public JobSeekerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public JobSeekerDTO toJobSeekerDTO(JobSeeker jobSeeker) {
        if (jobSeeker == null) return null;

        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO();
        jobSeekerDTO.setId(jobSeeker.getId());
        jobSeekerDTO.setUser(userMapper.toUserDTO(jobSeeker.getUser()));
        jobSeekerDTO.setFirstName(jobSeeker.getFirstName());
        jobSeekerDTO.setLastName(jobSeeker.getLastName());
        jobSeekerDTO.setResumePath(jobSeeker.getResumePath());

        return jobSeekerDTO;
    }

    public JobSeeker toJobSeekerEntity(JobSeekerDTO jobSeekerDTO, User user) {
        if (jobSeekerDTO == null) return null;

        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setUser(user);
        jobSeeker.setFirstName(jobSeekerDTO.getFirstName());
        jobSeeker.setLastName(jobSeekerDTO.getLastName());
        jobSeeker.setResumePath(jobSeekerDTO.getResumePath());

        return jobSeeker;
    }

    public List<JobSeekerDTO> toJobSeekerDTOList(List<JobSeeker> jobSeekers) {
        return jobSeekers.stream()
                .map(this::toJobSeekerDTO)
                .collect(Collectors.toList());
    }
}
