package com.spring.ai.eval.llm.service;

public record LlmEvaluationRequest(String input, String actual_output, String expected_output) {

}
