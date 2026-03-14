package com.resume.ai_resume_generator.model;

import java.util.List;

public class TechnicalSkills {

    private List<String> programmingLanguages;
    private List<String> webTechnologies;
    private List<String> toolsAndLibraries;

    public List<String> getProgrammingLanguages() { return programmingLanguages; }
    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public List<String> getWebTechnologies() { return webTechnologies; }
    public void setWebTechnologies(List<String> webTechnologies) {
        this.webTechnologies = webTechnologies;
    }

    public List<String> getToolsAndLibraries() { return toolsAndLibraries; }
    public void setToolsAndLibraries(List<String> toolsAndLibraries) {
        this.toolsAndLibraries = toolsAndLibraries;
    }
}
