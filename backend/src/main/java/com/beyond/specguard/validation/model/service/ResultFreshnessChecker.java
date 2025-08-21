package com.beyond.specguard.validation.model.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 실제로는 DB에서 resumeId의 최신 결과 타임스탬프/룰셋 버전 비교.
 */
@Component
public class ResultFreshnessChecker {
    public boolean isFresh(UUID resumeId) {
        // 데모: 항상 STALE로 간주 (운영에서는 로직 구현)
        return false;
    }
}
