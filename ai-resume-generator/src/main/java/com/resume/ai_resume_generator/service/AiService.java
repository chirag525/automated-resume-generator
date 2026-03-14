package com.resume.ai_resume_generator.service;

import com.resume.ai_resume_generator.model.ResumeRequest;
import com.resume.ai_resume_generator.util.PromptBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateSummary(ResumeRequest request) {
        String prompt = PromptBuilder.buildSummaryPrompt(request);
        return callGemini(prompt);
    }

    public String generateProjectSummary(String name, String technologies) {
        String prompt = PromptBuilder.buildProjectPrompt(name, technologies);
        return callGemini(prompt);
    }

    private String callGemini(String prompt) {

        String url = "https://generativelanguage.googleapis.com/v1/models/"
                + model + ":generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts",
                                List.of(Map.of("text", prompt)))
                )
        );

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, entity, Map.class);

        if (response.getBody() == null ||
                response.getBody().get("candidates") == null) {
            throw new RuntimeException("Invalid Gemini response");
        }

        List candidates = (List) response.getBody().get("candidates");

        if (candidates.isEmpty()) {
            throw new RuntimeException("Gemini returned empty response");
        }

        Map candidate = (Map) candidates.get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");

        if (parts == null || parts.isEmpty()) {
            throw new RuntimeException("Gemini response has no content");
        }

        Map firstPart = (Map) parts.get(0);

        return firstPart.get("text").toString();
    }
}