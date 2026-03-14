package com.resume.ai_resume_generator.template;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.resume.ai_resume_generator.model.Internship;
import com.resume.ai_resume_generator.model.Project;
import com.resume.ai_resume_generator.model.ResumeRequest;
import com.resume.ai_resume_generator.util.ImageUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Component("CLASSIC")
public class ClassicTemplate implements ResumeTemplateStrategy {

    @Override
    public byte[] generate(ResumeRequest request, MultipartFile photo) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 60, 50);
            PdfWriter.getInstance(document, out);
            document.open();

            // FONTS 
            Font nameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            //  PROFILE PHOTO
            if (request.getProfilePhotoBase64() != null &&
                    !request.getProfilePhotoBase64().isBlank()) {

                Image image = ImageUtil.decodeBase64Image(request.getProfilePhotoBase64());

                if (image != null) {
                    image.scaleToFit(100, 100);
                    float x = document.right() - 100;
                    float y = document.top() - 100;
                    image.setAbsolutePosition(x, y);
                    document.add(image);
                }
            }

            //  NAME + CONTACT 
            if (request.getPersonalInfo() != null) {

                Paragraph name = new Paragraph(
                        safe(request.getPersonalInfo().getFullName()), nameFont);
                name.setAlignment(Element.ALIGN_CENTER);
                name.setSpacingAfter(5f);
                document.add(name);

                String contactLine =
                        safe(request.getPersonalInfo().getEmail()) + " | " +
                                safe(request.getPersonalInfo().getPhone()) + " | " +
                                safe(request.getPersonalInfo().getLocation());

                Paragraph contact = new Paragraph(contactLine, normalFont);
                contact.setAlignment(Element.ALIGN_CENTER);
                contact.setSpacingAfter(15f);
                document.add(contact);
            }

            //  SUMMARY 
            if (request.getProfessionalSummary() != null &&
                    !request.getProfessionalSummary().isBlank()) {

                addSectionTitle(document, "PROFESSIONAL SUMMARY", sectionFont);

                Paragraph summary = new Paragraph(
                        safe(request.getProfessionalSummary()), normalFont);
                summary.setSpacingAfter(10f);
                document.add(summary);
            }

            //  TECHNICAL SKILLS 
            if (request.getTechnicalSkills() != null) {

                addSectionTitle(document, "TECHNICAL SKILLS", sectionFont);

                if (request.getTechnicalSkills().getProgrammingLanguages() != null &&
                        !request.getTechnicalSkills().getProgrammingLanguages().isEmpty()) {

                    document.add(new Paragraph(
                            "Languages: " + String.join(", ",
                                    request.getTechnicalSkills().getProgrammingLanguages()),
                            normalFont));
                }

                if (request.getTechnicalSkills().getWebTechnologies() != null &&
                        !request.getTechnicalSkills().getWebTechnologies().isEmpty()) {

                    document.add(new Paragraph(
                            "Web: " + String.join(", ",
                                    request.getTechnicalSkills().getWebTechnologies()),
                            normalFont));
                }

                if (request.getTechnicalSkills().getToolsAndLibraries() != null &&
                        !request.getTechnicalSkills().getToolsAndLibraries().isEmpty()) {

                    document.add(new Paragraph(
                            "Tools: " + String.join(", ",
                                    request.getTechnicalSkills().getToolsAndLibraries()),
                            normalFont));
                }

                document.add(Chunk.NEWLINE);
            }

            //  PROJECTS 
            if (request.getProjects() != null && !request.getProjects().isEmpty()) {

                addSectionTitle(document, "PROJECTS", sectionFont);

                for (Project p : request.getProjects()) {

                    Paragraph projectName =
                            new Paragraph(safe(p.getProjectName()), boldFont);
                    projectName.setSpacingAfter(3f);
                    document.add(projectName);

                    Paragraph summary =
                            new Paragraph(safe(p.getAiGeneratedSummary()), normalFont);
                    summary.setSpacingAfter(8f);
                    document.add(summary);
                }
            }

            //INTERNSHIPS
            if (request.getInternships() != null && !request.getInternships().isEmpty()) {

                addSectionTitle(document, "INTERNSHIPS", sectionFont);

                for (Internship internship : request.getInternships()) {

                    Paragraph role =
                            new Paragraph(safe(internship.getRole()), boldFont);
                    document.add(role);

                    Paragraph company =
                            new Paragraph(
                                    safe(internship.getCompanyName()) + " — " +
                                            safe(internship.getLocation()),
                                    normalFont);
                    document.add(company);

                    Paragraph duration =
                            new Paragraph(safe(internship.getDuration()), normalFont);
                    duration.setSpacingAfter(8f);
                    document.add(duration);
                }
            }

            // EDUCATION 
            if (request.getEducation() != null && !request.getEducation().isEmpty()) {

                addSectionTitle(document, "EDUCATION", sectionFont);

                request.getEducation().forEach(e -> {
                    try {

                        Paragraph edu = new Paragraph(
                                safe(e.getDegree()) + " - " +
                                        safe(e.getInstitution()) +
                                        " (" + safe(e.getYear()) + ")",
                                normalFont);

                        edu.setSpacingAfter(5f);
                        document.add(edu);

                    } catch (Exception ignored) {}
                });
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    //HELPER
    private void addSectionTitle(Document document, String title, Font font)
            throws DocumentException {

        Paragraph section = new Paragraph(title, font);
        section.setSpacingBefore(10f);
        section.setSpacingAfter(6f);
        document.add(section);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
