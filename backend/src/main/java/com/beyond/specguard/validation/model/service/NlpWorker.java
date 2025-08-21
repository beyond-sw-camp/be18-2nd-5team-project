package com.beyond.specguard.validation.model.service;

import com.beyond.specguard.validation.model.dto.ConsistencyCalculateRequest;
import com.beyond.specguard.validation.model.vo.JobPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NlpWorker {

    public void process(JobPayload job) {
        // 1) Resume 데이터 로드 (DB)
        // 2) NLP 서버 호출 (timeout/retry/backoff)
        // 3) 결과 저장 (consistency_result 테이블)
        // 4) Job 상태 업데이트
        ConsistencyCalculateRequest req = job.request();
        log.info("Processing jobId={}, resumeId={}, ruleset={}, priority={}",
                job.jobId(), job.resumeId(),
                req.rulesetVersion() == null ? "latest" : req.rulesetVersion(),
                req.priority() == null ? "NORMAL" : req.priority()
        );
        // TODO: 실제 구현
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        log.info("Job completed: {}", job.jobId());
    }
}
