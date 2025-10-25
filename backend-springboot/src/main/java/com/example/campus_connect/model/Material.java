package com.example.campus_connect.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "materials")
@Data
public class Material {

    @Id
    private String id;

    private String title;

    private String description;

    private String fileUrl; // URL to the uploaded file

    private String uploadedBy; // User ID (Faculty)

    private LocalDateTime uploadedAt;
}
