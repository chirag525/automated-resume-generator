package com.resume.ai_resume_generator.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PersonalInfo {

    @NotBlank
    private String fullName;

    private String location;

    @Email
    private String email;

    @NotBlank
    private String phone;

    private String linkedin;

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
}
