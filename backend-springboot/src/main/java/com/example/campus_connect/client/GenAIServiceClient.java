package com.example.campus_connect.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "genai-service", url = "${genai.service.url}")
public interface GenAIServiceClient {

    @PostMapping("/summarize")
    Map<String, Object> summarizeInterviewExperience(@RequestBody Map<String, String> request);

    @PostMapping("/chat")
    Map<String, Object> chatWithAI(@RequestBody Map<String, String> request);
}
