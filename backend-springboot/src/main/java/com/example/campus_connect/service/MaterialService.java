package com.example.campus_connect.service;

import com.example.campus_connect.model.Material;
import com.example.campus_connect.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material uploadMaterial(Material material, String uploadedBy) {
        material.setUploadedBy(uploadedBy);
        material.setUploadedAt(LocalDateTime.now());
        return materialRepository.save(material);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAllByOrderByUploadedAtDesc();
    }

    public Material getMaterialById(String id) {
        return materialRepository.findById(id).orElse(null);
    }
}
