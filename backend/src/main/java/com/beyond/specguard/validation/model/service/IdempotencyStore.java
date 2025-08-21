package com.beyond.specguard.validation.model.service;

public interface IdempotencyStore {
    IdempotencyRecord find(String idemKey, String fingerprint);
    void save(String idemKey, String fingerprint, String jobId);
}
