package com.example.campus_connect.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "interview_experiences")
@Data
public class InterviewExperience {

    @Id
    private String id;

    private String company;

    private String role;

    private String experience; // Detailed interview experience text

    private String mistakes;

    private String preparationTips;

    private String sharedBy; // User ID (Senior/Student)

    private LocalDateTime sharedAt;

    private boolean isPublic; // Whether juniors can view
}
