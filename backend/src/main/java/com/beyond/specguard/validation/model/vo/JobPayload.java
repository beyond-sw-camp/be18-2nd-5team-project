package com.beyond.specguard.validation.model.vo;

import com.beyond.specguard.validation.model.dto.ConsistencyCalculateRequest;

import java.time.Instant;
import java.util.UUID;

public record JobPayload(String jobId,
                         UUID resumeId,
                         ConsistencyCalculateRequest request,
                         String requestedBy,
                         Instant requestedAt)
{}
