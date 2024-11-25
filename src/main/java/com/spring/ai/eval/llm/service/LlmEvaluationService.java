package com.spring.ai.eval.llm.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.spring.ai.eval.llm.dto.LlmEvaluationResponse;
import com.spring.ai.eval.llm.dto.LlmEvaluationRequest;
import com.spring.ai.eval.llm.dto.PromptRequest;
import com.spring.ai.eval.llm.dto.PromptTuningRequest;
import com.spring.ai.eval.llm.dto.PromptTuningResult;

@Service
public class LlmEvaluationService {
	
	private static final Logger logger = LoggerFactory.getLogger(LlmEvaluationService.class);

	private final ChatClientFactory chatClientFactory;

	private final RestClient restClient;
	
	private final ConfigProperties props;

	@Value("classpath:/output/expected_output.st")
	private Resource expectedOutput;

//	@Value("classpath:/prompts/spring-prompt.st")
//	private Resource sbPromptTemplate;

	public LlmEvaluationService(ChatClientFactory chatClientFactory, ConfigProperties props) {
		this.chatClientFactory = chatClientFactory;
		this.props = props;
		this.restClient = RestClient.create(this.props.apiUrl());
	}
	
    public ChatResponse getLlmModelResponse(PromptRequest promptRequest, String model) throws IOException {
		ChatClient chatClient = chatClientFactory.getChatClient(model);
		return chatClient.prompt()
			            .system(s -> s.text(promptRequest.systemPrompt()))
			            .user(u -> u.text(promptRequest.userPrompt()))
			            .call()
			            .chatResponse();
    }
    
	public PromptTuningResult evaluateLLMResponse(PromptTuningRequest promptTuningRequest, String model) throws IOException {
		PromptRequest promptRequest = new PromptRequest(promptTuningRequest.userPrompt(), promptTuningRequest.systemPrompt());
		ChatResponse chatResp = getLlmModelResponse(promptRequest, model);
		String llmResp = chatResp.getResult().getOutput().getContent();
//		PromptMetadata metadata = chatResp.getMetadata().getPromptMetadata();
		LlmEvaluationResponse evaluationResult = evaluateResponse(promptTuningRequest, llmResp);
		
		if (Double.parseDouble(evaluationResult.score()) < 0.7) {
			PromptRequest improvementPromptRequest = new PromptRequest(
	                "Suggest improvements for the following prompt: " + promptTuningRequest.userPrompt(),
	                promptTuningRequest.systemPrompt()
	        );
	        String improvementSuggestion = getLlmModelResponse(
	        		improvementPromptRequest, model)
	                .getResult().getOutput().getContent();
	        return new PromptTuningResult(llmResp, evaluationResult, improvementSuggestion);
	    }
		return new PromptTuningResult(llmResp, evaluationResult, null);
	}
	
	private LlmEvaluationResponse evaluateResponse(PromptTuningRequest promptTuningRequest, String llmResp) throws IOException {
		try {
			LlmEvaluationRequest llmEvalReq = buildEvalRequest(promptTuningRequest, llmResp);
			return restClient.post().uri("evaluate/").body(llmEvalReq).retrieve().body(LlmEvaluationResponse.class);
		} catch (Exception e) {
			logger.error("Evaluation service error: " + e.getMessage());
			throw new IOException("Evaluation failed. Please try again later.", e);
		}
	}
	
	public LlmEvaluationRequest buildEvalRequest(PromptTuningRequest promptTuningRequest, String llmResponse) {
	    return new LlmEvaluationRequest(
	        promptTuningRequest.userPrompt(),
	        llmResponse,
	        promptTuningRequest.expectedOutput(),
	        null,
	        promptTuningRequest.evaluationSteps()
	    );
	}

}
