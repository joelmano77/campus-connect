package com.example.campus_connect.repository;

import com.example.campus_connect.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {
    List<Material> findAllByOrderByUploadedAtDesc();
}
