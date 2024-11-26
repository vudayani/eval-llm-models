package com.spring.ai.eval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.ai.eval.llm.service.LlmEvaluationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EvaluationIntegrationTest {
	
	@Autowired
    private LlmEvaluationService llmEvaluationService;
	
//	@DynamicPropertySource
//    static void registerApiKey(DynamicPropertyRegistry registry) {
//        String apiKey = System.getenv("OPENAI_API_KEY");
//        registry.add("spring.ai.openai.api-key", () -> apiKey);
//    }

//
//    @Test
//    public void evalOpenAiLlmModel() throws IOException {
//    	
//        String prompt = "add jpa functionality";
//
//        String modelResponse = llmEvaluationService.getLLMModelResponse(prompt, "openai");
//        Assert.notNull(modelResponse, "LLM model response should not be null");
//
//        EvaluationResult evalResponse = llmEvaluationService.evaluateLLMResponse(modelResponse, "openai");
//        Assert.notNull(evalResponse, "Evaluation response should not be null");
//
//        double threshold = 0.97;
//        assertThat(Double.parseDouble(evalResponse.score())).isGreaterThan(threshold);
//        System.out.println("Evaluation Result: " + evalResponse);
//    }
//    
//    @Test
//    public void evalAnthropicLlmModel() throws IOException {
//    	
//        String prompt = "add jpa functionality";
//
//        String modelResponse = llmEvaluationService.getLLMModelResponse(prompt, "anthropic");
//        Assert.notNull(modelResponse, "LLM model response should not be null");
//
//        EvaluationResult evalResponse = llmEvaluationService.evaluateLLMResponse(modelResponse, "anthropic");
//        Assert.notNull(evalResponse, "Evaluation response should not be null");
//
//        double threshold = 0.97;
//        assertThat(Double.parseDouble(evalResponse.score())).isGreaterThan(threshold);
//        System.out.println("Evaluation Result: " + evalResponse);
//    }
}

