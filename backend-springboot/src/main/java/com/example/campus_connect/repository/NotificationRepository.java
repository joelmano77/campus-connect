package com.example.campus_connect.repository;

import com.example.campus_connect.model.Notification;
import com.example.campus_connect.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByTargetRoleOrTargetRoleIsNullOrderByCreatedAtDesc(User.Role targetRole);
}
