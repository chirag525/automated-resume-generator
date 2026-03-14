package com.resume.ai_resume_generator.util;

import com.resume.ai_resume_generator.model.Project;
import com.resume.ai_resume_generator.model.ResumeRequest;

import java.util.stream.Collectors;

public class PromptBuilder {

    public static String buildSummaryPrompt(ResumeRequest request) {

        String skills = "Not Provided";

        if (request.getTechnicalSkills() != null &&
                request.getTechnicalSkills().getProgrammingLanguages() != null) {

            skills = request.getTechnicalSkills()
                    .getProgrammingLanguages()
                    .stream()
                    .collect(Collectors.joining(", "));
        }

        String projects = "None";

        if (request.getProjects() != null) {
            projects = request.getProjects().stream()
                    .map(Project::getProjectName)
                    .collect(Collectors.joining(", "));
        }

        return """
                You are an expert resume writer.

                Write a 3-4 line professional summary.
                Keep it impactful and ATS-friendly.
                Do not use bullet points.

                Skills: %s
                Projects: %s
                """.formatted(skills, projects);
    }

    public static String buildProjectPrompt(String name, String technologies) {

        if (name == null) name = "Project";
        if (technologies == null) technologies = "Various technologies";

        return """
                Write a 2-3 line professional resume project description.
                Highlight impact and technical strength.
                No bullet points.

                Project: %s
                Technologies: %s
                """.formatted(name, technologies);
    }
}