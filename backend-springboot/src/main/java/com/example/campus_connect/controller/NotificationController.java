package com.example.campus_connect.controller;

import com.example.campus_connect.model.Notification;
import com.example.campus_connect.model.User;
import com.example.campus_connect.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Notification created = notificationService.createNotification(notification, user.getId());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<Notification> notifications = notificationService.getNotificationsForRole(user.getRole());
        return ResponseEntity.ok(notifications);
    }
}
