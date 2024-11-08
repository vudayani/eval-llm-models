package com.spring.ai.eval.llm;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.spring.ai.eval.llm.service.LlmEvaluationService;

@RestController
public class OpenAiChatController {

    private final ChatClient chatClient;
    
    private final RestClient restClient;
    
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
    


    public OpenAiChatController(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
        this.restClient = RestClient.create("http://127.0.0.1:8000/");
    }

    @GetMapping("/openai")
    public String generateSpringCode(@RequestParam(value = "prompt", defaultValue = "add jpa functionality") String prompt) {
        return llmEvaluationService.getLLMModelResponse(prompt);
    }
    
	@GetMapping("/openai/eval")
	public String evaluateLLMResp(@RequestParam(value = "prompt", defaultValue = "add jpa functionality") String prompt)
			throws IOException {
		
		llmEvaluationService.evaluateLLMResponse(prompt);
		
		String resp = chatClient.prompt().system(sbPromptTemplate).user(prompt).call().content();

		Request req = new Request("add jpa functionality", resp,
				expectedOutput.getContentAsString(Charset.defaultCharset()));

		Response response = restClient.post().uri("evaluate/").body(req).retrieve().body(Response.class);
		System.out.println("Eval results " + response);

		return resp;
	}
}