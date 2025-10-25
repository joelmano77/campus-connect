package com.example.campus_connect.service;

import com.example.campus_connect.model.Notification;
import com.example.campus_connect.model.User;
import com.example.campus_connect.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(Notification notification, String postedBy) {
        notification.setPostedBy(postedBy);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForRole(User.Role role) {
        return notificationRepository.findByTargetRoleOrTargetRoleIsNullOrderByCreatedAtDesc(role);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
