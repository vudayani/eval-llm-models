package com.spring.ai.eval.llm.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatClientFactory {

    private final ChatClient openAIChatClient;
    private final ChatClient anthropicChatClient;

    public ChatClientFactory(
            @Qualifier("openAIChatClient") ChatClient openAIChatClient,
            @Qualifier("anthropicChatClient") ChatClient anthropicChatClient) {
        this.openAIChatClient = openAIChatClient;
        this.anthropicChatClient = anthropicChatClient;
    }

    public ChatClient getChatClient(String modelType) {
        if ("openai".equalsIgnoreCase(modelType)) {
            return openAIChatClient;
        } else if ("anthropic".equalsIgnoreCase(modelType)) {
            return anthropicChatClient;
        } else {
            throw new IllegalArgumentException("Invalid model type: " + modelType);
        }
    }
}

