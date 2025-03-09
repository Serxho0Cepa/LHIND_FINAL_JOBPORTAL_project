package com.jobportal.jobportalbackend.model.dto;

import java.util.List;

public class EmployerDTO {
    private Long id;
    private UserDTO user;
    private String companyName;
    private String companyDescription;
    private String companyWebsite;
    private List<JobDTO> postedJobs;


    public EmployerDTO() {}

    public EmployerDTO(Long id, UserDTO user, String companyName) {
        this.id = id;
        this.user = user;
        this.companyName = companyName;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyDescription() { return companyDescription; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }

    public String getCompanyWebsite() { return companyWebsite; }
    public void setCompanyWebsite(String companyWebsite) { this.companyWebsite = companyWebsite; }

    public List<JobDTO> getPostedJobs() { return postedJobs; }
    public void setPostedJobs(List<JobDTO> postedJobs) { this.postedJobs = postedJobs; }
}