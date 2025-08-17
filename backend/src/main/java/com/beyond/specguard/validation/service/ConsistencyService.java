package com.beyond.specguard.validation.service;

import com.beyond.specguard.validation.dto.ConsistencyResponse;
import org.springframework.stereotype.Service;

public interface ConsistencyService {
    ConsistencyResponse getConsistencyResult(String resumeId, String requesterId);
}
