package com.beyond.specguard.validation.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsistencyResponse {
    private String resumeId;
    private String userId;
    private int consistencyScore;
    private String analysisSummary;
    private LocalDateTime analyzedAt;
    private String resumeStatus;
}
