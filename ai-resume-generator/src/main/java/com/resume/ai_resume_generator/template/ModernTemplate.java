package com.resume.ai_resume_generator.template;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.resume.ai_resume_generator.model.Project;
import com.resume.ai_resume_generator.model.ResumeRequest;
import com.resume.ai_resume_generator.util.ImageUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.lowagie.text.pdf.ColumnText;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

@Component("MODERN")
public class ModernTemplate implements ResumeTemplateStrategy {

    @Override
    public byte[] generate(ResumeRequest request, MultipartFile photo) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            PdfContentByte canvas = writer.getDirectContent();

            //  LEFT SIDEBAR BACKGROUND 
            canvas.setColorFill(new Color(21, 94, 117));
            canvas.rectangle(0, 0, 180, PageSize.A4.getHeight());
            canvas.fill();

            //FONTS 
            Font whiteTitle = new Font(Font.HELVETICA, 16, Font.BOLD, Color.WHITE);
            Font whiteText = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.WHITE);

            Font headerFont = new Font(Font.HELVETICA, 22, Font.BOLD);
            Font sectionFont = new Font(Font.HELVETICA, 13, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);

            //  LEFT COLUMN CONTENT 
            float leftX = 20;
            float leftY = PageSize.A4.getHeight() - 50;

            // Profile Photo
            if (request.getProfilePhotoBase64() != null &&
                    !request.getProfilePhotoBase64().isBlank()) {

                Image image = ImageUtil.decodeBase64Image(
                        request.getProfilePhotoBase64());

                if (image != null) {
                    image.scaleToFit(100, 100);
                    image.setAbsolutePosition(40, leftY - 120);
                    document.add(image);
                    leftY -= 140;
                }
            }

            ColumnText leftColumn = new ColumnText(canvas);
            leftColumn.setSimpleColumn(leftX, 50, 170, leftY);

            leftColumn.addElement(new Paragraph("SUMMARY", whiteTitle));

            if (request.getProfessionalSummary() != null) {
                leftColumn.addElement(
                        new Paragraph(request.getProfessionalSummary(), whiteText));
            }

            leftColumn.go();

            //  RIGHT CONTENT 
            float rightStartX = 200;

            // Name
            if (request.getPersonalInfo() != null) {

                Paragraph name = new Paragraph(
                        safe(request.getPersonalInfo().getFullName()),
                        headerFont);
                name.setIndentationLeft(rightStartX);
                name.setSpacingAfter(5);
                document.add(name);

                Paragraph contact = new Paragraph(
                        safe(request.getPersonalInfo().getEmail()) + " | " +
                                safe(request.getPersonalInfo().getPhone()),
                        normalFont);
                contact.setIndentationLeft(rightStartX);
                contact.setSpacingAfter(20);
                document.add(contact);
            }

            // Projects Section
            if (request.getProjects() != null &&
                    !request.getProjects().isEmpty()) {

                Paragraph section = new Paragraph("WORK EXPERIENCE", sectionFont);
                section.setIndentationLeft(rightStartX);
                section.setSpacingAfter(10);
                document.add(section);

                for (Project p : request.getProjects()) {

                    Paragraph projectName =
                            new Paragraph(p.getProjectName(), sectionFont);
                    projectName.setIndentationLeft(rightStartX);
                    document.add(projectName);

                    Paragraph desc =
                            new Paragraph(p.getAiGeneratedSummary(), normalFont);
                    desc.setIndentationLeft(rightStartX + 10);
                    desc.setSpacingAfter(10);
                    document.add(desc);
                }
            }

            // Education
            if (request.getEducation() != null &&
                    !request.getEducation().isEmpty()) {

                Paragraph eduTitle = new Paragraph("EDUCATION", sectionFont);
                eduTitle.setIndentationLeft(rightStartX);
                eduTitle.setSpacingBefore(10);
                document.add(eduTitle);

                request.getEducation().forEach(e -> {
                    try {
                        Paragraph edu = new Paragraph(
                                e.getDegree() + " - " +
                                        e.getInstitution() +
                                        " (" + e.getYear() + ")",
                                normalFont);
                        edu.setIndentationLeft(rightStartX);
                        document.add(edu);
                    } catch (Exception ignored) {}
                });
            }

            // INTERNSHIPS 
            if (request.getInternships() != null &&
                    !request.getInternships().isEmpty()) {

                Paragraph section = new Paragraph("INTERNSHIPS", sectionFont);
                section.setIndentationLeft(rightStartX);
                section.setSpacingBefore(10);
                section.setSpacingAfter(10);
                document.add(section);

                request.getInternships().forEach(i -> {
                    try {

                        Paragraph role =
                                new Paragraph(i.getRole(), sectionFont);
                        role.setIndentationLeft(rightStartX);
                        document.add(role);

                        Paragraph company =
                                new Paragraph(
                                        i.getCompanyName() + " — " +
                                                i.getLocation(),
                                        normalFont);
                        company.setIndentationLeft(rightStartX);
                        document.add(company);

                        Paragraph duration =
                                new Paragraph(i.getDuration(), normalFont);
                        duration.setIndentationLeft(rightStartX);
                        duration.setSpacingAfter(8);
                        document.add(duration);

                    } catch (Exception ignored) {}
                });
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Modern template generation failed", e);
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
