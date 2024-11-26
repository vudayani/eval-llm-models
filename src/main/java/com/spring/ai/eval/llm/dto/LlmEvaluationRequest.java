package com.spring.ai.eval.llm.dto;

import java.util.List;

public record LlmEvaluationRequest(String input, String actual_output, String expected_output, List<String> retrieval_context,
		List<String> evaluationSteps) {

}
