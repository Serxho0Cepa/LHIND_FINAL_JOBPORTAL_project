package com.jobportal.jobportalbackend.service;

import com.jobportal.jobportalbackend.mapper.EmployerMapper;
import com.jobportal.jobportalbackend.mapper.UserMapper;
import com.jobportal.jobportalbackend.model.dto.EmployerDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.User;
import com.jobportal.jobportalbackend.model.enums.UserRole;
import com.jobportal.jobportalbackend.repository.EmployerRepository;
import com.jobportal.jobportalbackend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;
    private final UserRepository userRepository;
    private final EmployerMapper employerMapper;
    private final UserMapper userMapper;

    public EmployerService(EmployerRepository employerRepository,
                           UserRepository userRepository,
                           EmployerMapper employerMapper,
                           UserMapper userMapper) {
        this.employerRepository = employerRepository;
        this.userRepository = userRepository;
        this.employerMapper = employerMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    public EmployerDTO createEmployerProfile(Long userId, EmployerDTO employerDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.EMPLOYER) {
            throw new RuntimeException("User is not an employer");
        }

        Employer employer = employerMapper.toEmployerEntity(employerDTO, user);
        employer = employerRepository.save(employer);

        return employerMapper.toEmployerDTO(employer);
    }

    @Transactional(readOnly = true)
    public Page<EmployerDTO> searchEmployers(String companyName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employer> employers = employerRepository.findByCompanyNameContaining(companyName, pageable);

        return employers.map(employerMapper::toEmployerDTO);
    }
}
