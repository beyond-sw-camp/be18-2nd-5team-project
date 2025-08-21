package com.beyond.specguard.validation.model.vo;

public record ConsistencyTriggerOutcome(
        Type type,
        Object payload
) {
    public enum Type { ACCEPTED, REUSED, SKIPPED }
    public static ConsistencyTriggerOutcome accepted(Object payload) { return new ConsistencyTriggerOutcome(Type.ACCEPTED, payload); }
    public static ConsistencyTriggerOutcome reused(Object payload) { return new ConsistencyTriggerOutcome(Type.REUSED, payload); }
    public static ConsistencyTriggerOutcome skipped() { return new ConsistencyTriggerOutcome(Type.SKIPPED, null); }
}
