package com.resume.ai_resume_generator.service;

import com.resume.ai_resume_generator.model.Project;
import com.resume.ai_resume_generator.model.ResumeRequest;
import com.resume.ai_resume_generator.template.ResumeTemplateStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ResumeService {

    private final AiService aiService;
    private final Map<String, ResumeTemplateStrategy> templateMap;

    public ResumeService(AiService aiService,
                         Map<String, ResumeTemplateStrategy> templateMap) {
        this.aiService = aiService;
        this.templateMap = templateMap;
    }

    public byte[] createResume(ResumeRequest request,
                               MultipartFile photo) {

        // 1️⃣ Generate Professional Summary
        String summary;
        try {
            summary = aiService.generateSummary(request);
            if (summary == null || summary.isBlank()) {
                summary = "Experienced professional with strong technical skills and project expertise.";
            }
        } catch (Exception e) {
            summary = "Experienced professional with strong technical skills and project expertise.";
        }

        request.setProfessionalSummary(summary);

        // 2️⃣ Generate Project Summaries
        if (request.getProjects() != null) {
            for (Project project : request.getProjects()) {

                try {
                    String projectSummary =
                            aiService.generateProjectSummary(
                                    project.getProjectName(),
                                    project.getTechnologies()
                            );

                    if (projectSummary == null || projectSummary.isBlank()) {
                        projectSummary = "Developed and implemented this project using modern technologies.";
                    }

                    project.setAiGeneratedSummary(projectSummary);

                } catch (Exception e) {
                    project.setAiGeneratedSummary(
                            "Developed and implemented this project using modern technologies."
                    );
                }
            }
        }

        // 3️⃣ Select Template
        String templateKey = request.getTemplateType().name();
        ResumeTemplateStrategy strategy = templateMap.get(templateKey);

        if (strategy == null) {
            throw new RuntimeException("Template not found: " + templateKey);
        }

        // 4️⃣ Generate PDF
        byte[] pdf = strategy.generate(request, photo);

        if (pdf == null || pdf.length == 0) {
            throw new RuntimeException("PDF generation failed - empty file");
        }

        return pdf;
    }
}