package com.spring.ai.eval.llm;

import java.io.IOException;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.eval.llm.service.EvaluationResult;
import com.spring.ai.eval.llm.service.LlmEvaluationService;

@RestController
public class ChatController {

	@Value("classpath:/output/expected_output.st")
	private Resource expectedOutput;

	@Value("classpath:/output/actual_output.st")
	private Resource actualOutput;

	@Value("classpath:/prompts/spring-prompt.st")
	private Resource sbPromptTemplate;

	private LlmEvaluationService llmEvaluationService;

	public record Response(String score, String reason) {

	}

	public record Request(String input, String actual_output, String expected_output) {

	}

	public ChatController(@Qualifier("openAIChatClient") ChatClient chatClient, LlmEvaluationService llmEvaluationService) {
		this.llmEvaluationService = llmEvaluationService;
	}

	@GetMapping("/add")
	public String generateSpringCode(@RequestParam(value = "prompt", defaultValue = "add jpa functionality") String prompt,
			@RequestParam(value = "modelType", defaultValue = "openai") String modelType) {
		return llmEvaluationService.getLLMModelResponse(prompt, modelType);
	}

	@GetMapping("/eval")
	public EvaluationResult evaluateLLMResp(@RequestParam(value = "prompt", defaultValue = "add jpa functionality") String prompt,
			@RequestParam(value = "modelType", defaultValue = "openai") String modelType) throws IOException {

		return llmEvaluationService.evaluateLLMResponse(prompt, modelType);

	}
}