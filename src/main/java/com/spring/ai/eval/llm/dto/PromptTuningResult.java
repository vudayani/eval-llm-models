package com.spring.ai.eval.llm.dto;

public record PromptTuningResult(String llmResponse, LlmEvaluationResponse evalResponse, String improvementSuggestion) {

}
