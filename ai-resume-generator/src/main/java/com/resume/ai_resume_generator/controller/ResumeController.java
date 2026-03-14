package com.resume.ai_resume_generator.controller;

import com.resume.ai_resume_generator.model.ResumeRequest;
import com.resume.ai_resume_generator.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(
            value = "/generate",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> generateResume(
            @Valid @RequestBody ResumeRequest request
    ) {

        byte[] pdfBytes = resumeService.createResume(request, null);

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Generated PDF is empty!");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}