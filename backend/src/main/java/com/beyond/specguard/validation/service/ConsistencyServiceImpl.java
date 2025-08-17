package com.beyond.specguard.validation.service;

import com.beyond.specguard.validation.dto.ConsistencyResponse;

import java.time.LocalDateTime;

public class ConsistencyServiceImpl implements ConsistencyService {
    @Override
    public ConsistencyResponse getConsistencyResult(String resumeId, String requesterId) {
        // TODO: DB 조회 및 검증 로직 구현
        // 1. resumeId로 이력서 조회
        // 2. requesterId 소속 기업과 resume 소속 기업 검증
        // 3. 상태가 COMPLETED 이상인지 확인
        // 4. 정합성 점수/분석 요약 반환

        ConsistencyResponse response = new ConsistencyResponse();
        response.setResumeId(resumeId);
        response.setUserId("u12345"); // 예시 값
        response.setConsistencyScore(87);
        response.setAnalysisSummary("이력서와 GitHub 활동이 대부분 일치함");
        response.setAnalyzedAt(LocalDateTime.now());
        response.setResumeStatus("REVIEWED");

        return response;
    }
}
