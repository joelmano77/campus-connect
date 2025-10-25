package com.example.campus_connect.controller;

import com.example.campus_connect.model.Material;
import com.example.campus_connect.model.User;
import com.example.campus_connect.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY')")
    public ResponseEntity<Material> uploadMaterial(@RequestBody Material material, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Material uploaded = materialService.uploadMaterial(material, user.getId());
        return ResponseEntity.ok(uploaded);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        List<Material> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
        Material material = materialService.getMaterialById(id);
        if (material != null) {
            return ResponseEntity.ok(material);
        }
        return ResponseEntity.notFound().build();
    }
}
