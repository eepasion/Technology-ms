package com.example.technology.infrastructure.config.auth;

import com.example.technology.domain.enums.ErrorMessages;
import com.example.technology.domain.exceptions.BusinessException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtSecretReader {

    private JwtSecretReader() {
        throw new IllegalStateException("Utility class");
    }

    public static String readSecretKey() {
        ClassPathResource resource = new ClassPathResource("secret.key");
        try {
            String encodedKey = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8).trim();
            return new String(Base64.getDecoder().decode(encodedKey), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BusinessException(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
