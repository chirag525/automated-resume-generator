package com.resume.ai_resume_generator.util;

import com.lowagie.text.Image;
import java.util.Base64;

public class ImageUtil {

    public static Image decodeBase64Image(String base64) {

        try {

            if (base64 == null || base64.isBlank()) {
                return null;
            }

            if (base64.contains(",")) {
                base64 = base64.split(",")[1];
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64);
            Image image = Image.getInstance(imageBytes);

            return image;

        } catch (Exception e) {
            throw new RuntimeException("Failed to decode image", e);
        }
    }
}