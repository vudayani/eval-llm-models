package com.spring.ai.eval.llm.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class LlmEvaluationService {

	private final ChatClientFactory chatClientFactory;

	private final RestClient restClient;
	
	private final ConfigProperties props;

	@Value("classpath:/output/expected_output.st")
	private Resource expectedOutput;

	@Value("classpath:/output/actual_output.st")
	private Resource actualOutput;

	@Value("classpath:/prompts/spring-prompt.st")
	private Resource sbPromptTemplate;

	public LlmEvaluationService(ChatClientFactory chatClientFactory, ConfigProperties props) {
		this.chatClientFactory = chatClientFactory;
		this.props = props;
		this.restClient = RestClient.create(this.props.apiUrl());
	}
	
    public String getLLMModelResponse(String prompt, String modelType) {
    	ChatClient chatClient = chatClientFactory.getChatClient(modelType);
    	return chatClient.prompt()
        		.system(sbPromptTemplate)
                .user(prompt)
                .call()
                .content();
    }
    
	public EvaluationResult evaluateLLMResponse(String prompt, String modelType) throws IOException {
		String llmResp = getLLMModelResponse(prompt, modelType);
		LlmEvaluationRequest req = new LlmEvaluationRequest("add jpa functionality", llmResp,
				expectedOutput.getContentAsString(Charset.defaultCharset()));

		return restClient.post().uri("evaluate/").body(req).retrieve().body(EvaluationResult.class);

	}

}
