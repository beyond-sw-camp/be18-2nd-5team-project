package com.beyond.specguard.validation.controller;

import com.beyond.specguard.validation.model.dto.ConsistencyCalculateRequest;
import com.beyond.specguard.validation.model.dto.ConsistencyResponse;
import com.beyond.specguard.validation.model.service.ConsistencyService;
import com.beyond.specguard.validation.model.vo.ConsistencyTriggerOutcome;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/consistency")
public class ConsistencyScoreController {
    private final ConsistencyService consistencyService;

    public ConsistencyScoreController(ConsistencyService consistencyService) {
        this.consistencyService = consistencyService;
    }

    /**
     * [정합성 점수 조회 API]
     * GET /api/v1/consistency/{resumeId}
     */
    @GetMapping(path = "/{resumeId}")
    public ResponseEntity<ConsistencyResponse> getConsistencyScore(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String resumeId
    ) {
        String requesterId = extractUserIdFromToken(authorizationHeader);

        ConsistencyResponse response = consistencyService.getConsistencyResult(resumeId, requesterId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{resumeId}/calculate",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateConsistencyScore(
            @PathVariable UUID resumeId,
            @RequestHeader(name = "Idempotency-Key") String idemKey,
            @Valid @RequestBody ConsistencyCalculateRequest body,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String requesterId = extractUserIdFromToken(authorizationHeader);
        log.debug("resumeId : {}", resumeId);
        ConsistencyTriggerOutcome outcome = consistencyService.triggerCalculation(resumeId, idemKey, body, requesterId);

        return switch (outcome.type()) {
            case ACCEPTED -> ResponseEntity.status(HttpStatus.ACCEPTED).body(outcome.payload());
            case REUSED   -> ResponseEntity.ok(outcome.payload());
            case SKIPPED  -> ResponseEntity.noContent()
                    .header("SG-Skip-Reason", "UP_TO_DATE").build();
        };
    }

    // JWT 토큰에서 사용자 ID 추출하는 메서드
    private String extractUserIdFromToken(String token) {
        // TODO: 실제 JWT 파싱 로직으로 교체
        return "u12345";
    }

}
