package com.beyond.specguard.validation.model.service;

import com.beyond.specguard.validation.model.dto.ConsistencyCalculateRequest;
import com.beyond.specguard.validation.model.dto.ConsistencyResponse;
import com.beyond.specguard.validation.model.vo.ConsistencyTriggerOutcome;
import com.beyond.specguard.validation.model.vo.JobId;
import com.beyond.specguard.validation.model.vo.JobPayload;
import com.beyond.specguard.validation.model.vo.JobResponse;
import com.beyond.specguard.validation.model.vo.RecalcPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsistencyServiceImpl implements ConsistencyService {

    private final IdempotencyStore idemStore;
    private final ResultFreshnessChecker freshnessChecker;
    private final JobQueue jobQueue;

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

    public ConsistencyTriggerOutcome triggerCalculation(UUID resumeId,
                                                        String idemKey,
                                                        ConsistencyCalculateRequest req,
                                                        String requestedBy) {

        // 1) 최신 결과면 204
        if (shouldSkipByPolicy(resumeId, req)) {
            return ConsistencyTriggerOutcome.skipped();
        }

        // 2) 멱등키 체크 (resumeId + body 해시)
        String fingerprint = Fingerprint.hash(resumeId, req);
        IdempotencyRecord existing = idemStore.find(idemKey, fingerprint);
        if (existing != null) {
            // 기존 job 재사용
            return ConsistencyTriggerOutcome.reused(
                    JobResponse.ofReused(existing.jobId(), resumeId)
            );
        }

        // 3) 잡 생성 + 큐 투입
        String jobId = JobId.newId();
        idemStore.save(idemKey, fingerprint, jobId);

        jobQueue.enqueue(new JobPayload(jobId, resumeId, req, requestedBy, Instant.now()));

        // 4) 202 Accepted
        String appliedPolicy = req.recalcPolicy() == null ? "IF_STALE" : req.recalcPolicy().name();
        String ruleset = req.rulesetVersion() == null ? "latest" : req.rulesetVersion();
        int eta = 30; // 예시 ETA, 실제는 큐 상태/우선순위 기반 계산
        return ConsistencyTriggerOutcome.accepted(
                JobResponse.ofQueued(jobId, resumeId, appliedPolicy, ruleset, eta)
        );
    }

    private boolean shouldSkipByPolicy(UUID resumeId, ConsistencyCalculateRequest req) {
        RecalcPolicy policy = req.recalcPolicy() == null ? RecalcPolicy.IF_STALE : req.recalcPolicy();
        return switch (policy) {
            case NEVER -> true; // 요청자가 절대 재계산 원치 않음
            case IF_STALE -> freshnessChecker.isFresh(resumeId);
            case ALWAYS -> false;
        };
    }
}
