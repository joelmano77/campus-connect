package com.example.campus_connect.controller;

import com.example.campus_connect.model.InterviewExperience;
import com.example.campus_connect.model.User;
import com.example.campus_connect.service.InterviewExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interview-experiences")
public class InterviewExperienceController {

    private final InterviewExperienceService interviewExperienceService;

    public InterviewExperienceController(InterviewExperienceService interviewExperienceService) {
        this.interviewExperienceService = interviewExperienceService;
    }

    @PostMapping
    public ResponseEntity<InterviewExperience> shareExperience(@RequestBody InterviewExperience experience, Authentication auth) {
        User user = (User) auth.getPrincipal();
        InterviewExperience shared = interviewExperienceService.shareExperience(experience, user.getId());
        return ResponseEntity.ok(shared);
    }

    @GetMapping
    public ResponseEntity<List<InterviewExperience>> getPublicExperiences() {
        List<InterviewExperience> experiences = interviewExperienceService.getPublicExperiences();
        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/my")
    public ResponseEntity<List<InterviewExperience>> getMyExperiences(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<InterviewExperience> experiences = interviewExperienceService.getUserExperiences(user.getId());
        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/{id}/summarize")
    public ResponseEntity<Map<String, Object>> getSummarizedExperience(@PathVariable String id) {
        Map<String, Object> summary = interviewExperienceService.getSummarizedExperience(id);
        return ResponseEntity.ok(summary);
    }
}
