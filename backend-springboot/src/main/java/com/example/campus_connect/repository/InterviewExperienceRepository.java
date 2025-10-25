package com.example.campus_connect.repository;

import com.example.campus_connect.model.InterviewExperience;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewExperienceRepository extends MongoRepository<InterviewExperience, String> {
    List<InterviewExperience> findByIsPublicTrueOrderBySharedAtDesc();
    List<InterviewExperience> findBySharedByOrderBySharedAtDesc(String sharedBy);
}
