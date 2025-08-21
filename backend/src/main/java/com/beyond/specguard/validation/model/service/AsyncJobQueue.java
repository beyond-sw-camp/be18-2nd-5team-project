package com.beyond.specguard.validation.model.service;

import com.beyond.specguard.validation.model.vo.JobPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncJobQueue implements JobQueue {

    private final NlpWorker nlpWorker;

    @Override
    public void enqueue(JobPayload payload) {
        try {
            log.info("Job queued: {}", payload.jobId());
            nlpWorker.process(payload);
        } catch (Exception e) {
            log.error("Job failed: {}", payload.jobId(), e);
            // 실패 시 DLQ/재시도 로직 연결 가능
        }
    }
}
