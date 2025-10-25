package com.example.campus_connect.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private String id;

    private String title;

    private String content;

    private String postedBy; // User ID

    private LocalDateTime createdAt;

    private User.Role targetRole; // ADMIN, FACULTY, STUDENT, or null for all
}
