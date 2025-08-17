package com.beyond.specguard.validation.controller;

import com.beyond.specguard.validation.dto.ConsistencyRequest;
import com.beyond.specguard.validation.dto.ConsistencyResponse;
import com.beyond.specguard.validation.service.ConsistencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // JWT 토큰에서 사용자 ID 추출하는 메서드
    private String extractUserIdFromToken(String token) {
        // TODO: 실제 JWT 파싱 로직으로 교체
        return "u12345";
    }

}
