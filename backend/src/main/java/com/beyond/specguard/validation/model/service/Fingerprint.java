package com.beyond.specguard.validation.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

public class Fingerprint {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String hash(UUID resumeId, Object body) {
        try {
            String json = MAPPER.writeValueAsString(body);
            String input = resumeId + "|" + json;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Fingerprint error", e);
        }
    }
}
