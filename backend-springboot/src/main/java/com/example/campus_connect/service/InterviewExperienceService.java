package com.example.campus_connect.service;

import com.example.campus_connect.client.GenAIServiceClient;
import com.example.campus_connect.model.InterviewExperience;
import com.example.campus_connect.repository.InterviewExperienceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class InterviewExperienceService {

    private final InterviewExperienceRepository interviewExperienceRepository;
    private final GenAIServiceClient genAIServiceClient;

    public InterviewExperienceService(InterviewExperienceRepository interviewExperienceRepository, GenAIServiceClient genAIServiceClient) {
        this.interviewExperienceRepository = interviewExperienceRepository;
        this.genAIServiceClient = genAIServiceClient;
    }

    public InterviewExperience shareExperience(InterviewExperience experience, String sharedBy) {
        experience.setSharedBy(sharedBy);
        experience.setSharedAt(LocalDateTime.now());
        return interviewExperienceRepository.save(experience);
    }

    public List<InterviewExperience> getPublicExperiences() {
        return interviewExperienceRepository.findByIsPublicTrueOrderBySharedAtDesc();
    }

    public List<InterviewExperience> getUserExperiences(String sharedBy) {
        return interviewExperienceRepository.findBySharedByOrderBySharedAtDesc(sharedBy);
    }

    public Map<String, Object> getSummarizedExperience(String experienceId) {
        InterviewExperience experience = interviewExperienceRepository.findById(experienceId).orElse(null);
        if (experience == null) {
            return Map.of("error", "Experience not found");
        }
        return genAIServiceClient.summarizeInterviewExperience(Map.of("text", experience.getExperience()));
    }
}
