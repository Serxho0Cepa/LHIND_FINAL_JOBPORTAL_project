package com.jobportal.jobportalbackend.model.dto;

import java.util.List;

public class JobSeekerDTO {
    private Long id;
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String resumePath;
    private List<JobApplicationDTO> jobApplications;


    public JobSeekerDTO() {}

    public JobSeekerDTO(Long id, UserDTO user, String firstName, String lastName) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getResumePath() { return resumePath; }
    public void setResumePath(String resumePath) { this.resumePath = resumePath; }

    public List<JobApplicationDTO> getJobApplications() { return jobApplications; }
    public void setJobApplications(List<JobApplicationDTO> jobApplications) { this.jobApplications = jobApplications; }
}

