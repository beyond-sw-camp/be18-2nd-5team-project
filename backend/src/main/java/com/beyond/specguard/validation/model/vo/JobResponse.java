package com.beyond.specguard.validation.model.vo;

import java.util.UUID;

public record JobResponse(
        String jobId,
        UUID resumeId,
        String status, // QUEUED | PROCESSING | COMPLETED | FAILED (초기에는 QUEUED/PROCESSING)
        Integer estimatedReadySec,
        String recalcPolicyApplied,
        String rulesetVersion,
        Links links,
        Boolean reused
) {
    public record Links(String self, String result) {}

    public static JobResponse ofQueued(String jobId, UUID resumeId, String policy, String ruleset, int etaSec) {
        return new JobResponse(
                jobId, resumeId, "QUEUED", etaSec, policy, ruleset,
                new Links("/api/v1/jobs/" + jobId,
                        "/api/v1/consistency/" + resumeId),
                null
        );
    }

    public static JobResponse ofReused(String jobId, UUID resumeId) {
        return new JobResponse(
                jobId, resumeId, "PROCESSING", null, null, null,
                new Links("/api/v1/jobs/" + jobId,
                        "/api/v1/consistency/" + resumeId),
                true
        );
    }
}
