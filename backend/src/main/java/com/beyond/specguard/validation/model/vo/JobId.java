package com.beyond.specguard.validation.model.vo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public final class JobId {
    private static final AtomicLong SEQ = new AtomicLong();
    public static String newId() {
        String ts = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss").format(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        long n = SEQ.updateAndGet(v -> (v + 1) % 100000);
        return "cns-" + ts + "-" + String.format("%05d", n);
    }
}
