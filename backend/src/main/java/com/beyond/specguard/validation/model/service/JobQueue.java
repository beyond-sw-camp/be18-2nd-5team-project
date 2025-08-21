package com.beyond.specguard.validation.model.service;

import com.beyond.specguard.validation.model.vo.JobPayload;

public interface JobQueue {
    void enqueue(JobPayload payload);
}
