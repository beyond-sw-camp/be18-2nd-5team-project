package com.beyond.specguard.validation.model.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: Redis 전환
 * 키 예시: idem:{idemKey}:{fingerprint} -> jobId (TTL 24h)
 */

@Component
public class InMemoryIdempotencyStore implements IdempotencyStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public IdempotencyRecord find(String idemKey, String fingerprint) {
        String key = key(idemKey, fingerprint);
        String jobId = store.get(key);
        return jobId == null ? null : new IdempotencyRecord(jobId);
    }

    @Override
    public void save(String idemKey, String fingerprint, String jobId) {
        store.putIfAbsent(key(idemKey, fingerprint), jobId);
    }

    private String key(String idemKey, String fingerprint) {
        return idemKey + "::" + fingerprint;
    }
}
