package com.spring.ai.eval.llm.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LlmEvaluationService {

	private final ChatClient chatClient;

	private final RestClient restClient;

	@Value("classpath:/output/expected_output.st")
	private Resource expectedOutput;

	@Value("classpath:/output/actual_output.st")
	private Resource actualOutput;

	public record EvaluationResult(String score, String reason) {

	}

	public record Request(String input, String actual_output, String expected_output) {

	}

	@Value("classpath:/prompts/spring-prompt.st")
	private Resource sbPromptTemplate;

	public LlmEvaluationService(@Qualifier("openAIChatClient") ChatClient chatClient) {
		this.chatClient = chatClient;
		this.restClient = RestClient.create("http://127.0.0.1:8000/");
	}
	
    public String getLLMModelResponse(String prompt) {
    	return chatClient.prompt()
        		.system(sbPromptTemplate)
                .user(prompt)
                .call()
                .content();
    }
    
	public EvaluationResult evaluateLLMResponse(String prompt) throws IOException {

		String llmResp = getLLMModelResponse(prompt);
		Request req = new Request("add jpa functionality", llmResp,
				expectedOutput.getContentAsString(Charset.defaultCharset()));

		return restClient.post().uri("evaluate/").body(req).retrieve().body(EvaluationResult.class);

	}

}
