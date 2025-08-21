package com.beyond.specguard.validation.model.dto;

import com.beyond.specguard.validation.model.vo.Priority;
import com.beyond.specguard.validation.model.vo.RecalcPolicy;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record ConsistencyCalculateRequest(
        String rulesetVersion,
        RecalcPolicy recalcPolicy,
        List<@NotBlank String> includeSections,
        NlpHints nlpHints,
        Priority priority,
        @Min(1) @Max(120) Integer timeoutSec
) {
    public record NlpHints(
            @Pattern(regexp = "KO|EN|JA") String language
    ) {}
}
