package com.resume.ai_resume_generator.template;

import com.resume.ai_resume_generator.model.ResumeRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeTemplateStrategy {

    byte[] generate(ResumeRequest request,
                    MultipartFile photo);
}