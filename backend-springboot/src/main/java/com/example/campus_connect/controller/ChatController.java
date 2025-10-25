package com.example.campus_connect.controller;

import com.example.campus_connect.client.GenAIServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GenAIServiceClient genAIServiceClient;

    public ChatController(GenAIServiceClient genAIServiceClient) {
        this.genAIServiceClient = genAIServiceClient;
    }

    @PostMapping("/chat")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String, Object>> chatWithAI(@RequestBody Map<String, String> request, Authentication auth) {
        Map<String, Object> response = genAIServiceClient.chatWithAI(request);
        return ResponseEntity.ok(response);
    }
}
