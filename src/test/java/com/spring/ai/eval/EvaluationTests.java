package com.spring.ai.eval;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.ai.eval.llm.service.LlmEvaluationService;

@SpringBootTest
public class EvaluationTests {
	
	@Autowired
    private LlmEvaluationService llmEvaluationService;
	
	@Autowired
	private OpenAiChatModel openAiChatModel;
	
	@Autowired
	private AnthropicChatModel anthropicChatModel;
	
//	@Test
//    public void evalOpenAiLlmModel() throws IOException {
//    	
//        String prompt = "add jpa functionality";
//
//        String modelResponse = llmEvaluationService.getLLMModelResponse(prompt, "openai");
//        var relevancyEvaluator = new RelevancyEvaluator(ChatClient.builder(openAiChatModel));
//        Assert.notNull(modelResponse, "LLM model response should not be null");
//        
//        EvaluationRequest evaluationRequest = new EvaluationRequest(
//        		prompt,
//        		modelResponse);
//
//
//        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
//
//        assertTrue(evaluationResponse.isPass(),
//            "Response is not relevant to the asked question.\n" +
//                "Question: " + prompt + "\n" +
//                "Response: " + modelResponse);
//    }
//	
//	@Test
//    public void evalAnthropicLlmModel() throws IOException {
//    	
//        String prompt = "add jpa functionality";
//
//        String modelResponse = llmEvaluationService.getLLMModelResponse(prompt, "anthropic");
//        var relevancyEvaluator = new RelevancyEvaluator(ChatClient.builder(anthropicChatModel));
//        Assert.notNull(modelResponse, "LLM model response should not be null");
//        
//        EvaluationRequest evaluationRequest = new EvaluationRequest(
//        		prompt,
//        		modelResponse);
//
//
//        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
//
//        assertTrue(evaluationResponse.isPass(),
//            "Response is not relevant to the asked question.\n" +
//                "Question: " + prompt + "\n" +
//                "Response: " + modelResponse);
//    }

}
