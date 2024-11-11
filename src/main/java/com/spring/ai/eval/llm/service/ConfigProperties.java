package com.spring.ai.eval.llm.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "evaluation")
public record ConfigProperties(String apiUrl) {

}
