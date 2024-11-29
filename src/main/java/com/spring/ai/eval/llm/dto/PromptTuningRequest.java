package com.spring.ai.eval.llm.dto;

import java.util.List;

public record PromptTuningRequest(String userPrompt, String systemPrompt, List<String> evaluationCriteria) {
}
