package com.spring.ai.eval.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class AnthropicChatController {

    private final ChatClient chatClient;
    
    @Value("classpath:/prompts/spring-prompt.st")
    private Resource sbPromptTemplate;

    public AnthropicChatController(@Qualifier("anthropicChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/claude")
    public Flux<String> generateSpringCode(@RequestParam(value = "message", defaultValue = "add jpa functionality") String message) {
        return chatClient.prompt()
        		.system(sbPromptTemplate)
                .user(message)
                .stream()
//                .call()
                .content();
    }
    
    @GetMapping("/stream")
    public Flux<String> chatWithStream(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
