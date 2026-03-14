package com.resume.ai_resume_generator.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ResumeRequest {

    //  PROFILE PHOTO 
    private String profilePhotoBase64;

    public String getProfilePhotoBase64() {
        return profilePhotoBase64;
    }

    public void setProfilePhotoBase64(String profilePhotoBase64) {
        this.profilePhotoBase64 = profilePhotoBase64;
    }

    //PERSONAL INFO 
    @NotNull(message = "Personal information is required")
    @Valid
    private PersonalInfo personalInfo;

    // AI GENERATED SUMMARY 
    private String professionalSummary;

    //  TECHNICAL SKILLS
    @Valid
    private TechnicalSkills technicalSkills;

    //  SOFT SKILLS 
    private List<String> softSkills;

    // PROJECTS
    @Valid
    private List<Project> projects;

    // EDUCATION
    @Valid
    private List<Education> education;

    // CERTIFICATIONS
    @Valid
    private List<Certification> certifications;

    //  INTERNSHIPS
    @Valid
    private List<Internship> internships;

    //  RESPONSIBILITIES 
    @Valid
    private List<Responsibility> responsibilities;

    //  TEMPLATE TYPE
    @NotNull(message = "Template type must be selected")
    private ResumeTemplate templateType;


   
    // GETTERS & SETTERS
   

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public TechnicalSkills getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(TechnicalSkills technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public List<String> getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(List<String> softSkills) {
        this.softSkills = softSkills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

   
    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public List<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public ResumeTemplate getTemplateType() {
        return templateType;
    }

    public void setTemplateType(ResumeTemplate templateType) {
        this.templateType = templateType;
    }
}
