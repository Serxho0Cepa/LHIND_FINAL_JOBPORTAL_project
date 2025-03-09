package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.mapper.JobSeekerMapper;
import com.jobportal.jobportalbackend.model.dto.JobSeekerDTO;
import com.jobportal.jobportalbackend.model.entity.JobSeeker;
import com.jobportal.jobportalbackend.model.entity.User;
import com.jobportal.jobportalbackend.model.enums.UserRole;
import com.jobportal.jobportalbackend.repository.JobSeekerRepository;
import com.jobportal.jobportalbackend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class JobSeekerService {
    private final JobSeekerRepository jobSeekerRepository;
    private final UserRepository userRepository;
    private final JobSeekerMapper jobSeekerMapper;

    public JobSeekerService(JobSeekerRepository jobSeekerRepository,
                            UserRepository userRepository,
                            JobSeekerMapper jobSeekerMapper) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.userRepository = userRepository;
        this.jobSeekerMapper = jobSeekerMapper;
    }

    @Transactional
    public JobSeekerDTO createJobSeekerProfile(Long userId, JobSeekerDTO jobSeekerDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.JOB_SEEKER) {
            throw new RuntimeException("User is not a job seeker");
        }

        JobSeeker jobSeeker = jobSeekerMapper.toJobSeekerEntity(jobSeekerDTO, user);
        jobSeeker = jobSeekerRepository.save(jobSeeker);

        return jobSeekerMapper.toJobSeekerDTO(jobSeeker);
    }


    @Transactional
    public JobSeekerDTO uploadResume(Long jobSeekerId, MultipartFile file) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        if (!fileExtension.equalsIgnoreCase("pdf")) {
            throw new RuntimeException("Only PDF files are allowed");
        }

        try {

            String UPLOAD_DIR = "resources/resumes/";

            File uploadDirectory = new File(UPLOAD_DIR);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String fileName = jobSeekerId + ".pdf";
            Path filePath = Paths.get(uploadDirectory.getAbsolutePath(), fileName);

            Files.write(filePath, file.getBytes());

            jobSeeker.setResumePath(filePath.toString());
            jobSeeker = jobSeekerRepository.save(jobSeeker);

            return jobSeekerMapper.toJobSeekerDTO(jobSeeker);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }



}
