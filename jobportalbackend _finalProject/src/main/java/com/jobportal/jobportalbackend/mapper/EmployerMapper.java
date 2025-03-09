package com.jobportal.jobportalbackend.mapper;

import com.jobportal.jobportalbackend.model.dto.EmployerDTO;
import com.jobportal.jobportalbackend.model.entity.Employer;
import com.jobportal.jobportalbackend.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployerMapper {
    private final UserMapper userMapper;

    public EmployerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public EmployerDTO toEmployerDTO(Employer employer) {
        if (employer == null) return null;

        EmployerDTO employerDTO = new EmployerDTO();
        employerDTO.setId(employer.getId());
        employerDTO.setUser(userMapper.toUserDTO(employer.getUser()));
        employerDTO.setCompanyName(employer.getCompanyName());
        employerDTO.setCompanyDescription(employer.getCompanyDescription());
        employerDTO.setCompanyWebsite(employer.getCompanyWebsite());

        return employerDTO;
    }

    public Employer toEmployerEntity(EmployerDTO employerDTO, User user) {
        if (employerDTO == null) return null;

        Employer employer = new Employer();
        employer.setUser(user);
        employer.setCompanyName(employerDTO.getCompanyName());
        employer.setCompanyDescription(employerDTO.getCompanyDescription());
        employer.setCompanyWebsite(employerDTO.getCompanyWebsite());

        return employer;
    }

    public List<EmployerDTO> toEmployerDTOList(List<Employer> employers) {
        return employers.stream()
                .map(this::toEmployerDTO)
                .collect(Collectors.toList());
    }
}
