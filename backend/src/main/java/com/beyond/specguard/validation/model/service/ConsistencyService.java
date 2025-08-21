package com.beyond.specguard.validation.model.service;

import com.beyond.specguard.validation.model.dto.ConsistencyCalculateRequest;
import com.beyond.specguard.validation.model.dto.ConsistencyResponse;
import com.beyond.specguard.validation.model.vo.ConsistencyTriggerOutcome;

import java.util.UUID;

public interface ConsistencyService {
    ConsistencyResponse getConsistencyResult(String resumeId, String requesterId);
    ConsistencyTriggerOutcome triggerCalculation(UUID resumeId,
                                                 String idemKey,
                                                 ConsistencyCalculateRequest req,
                                                 String requestedBy);

}
