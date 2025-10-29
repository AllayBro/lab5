package com.example.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final List<Map<String, Object>> notifications = new ArrayList<>();

    @PostMapping("/notify")
    public ResponseEntity<?> receiveNotification(@RequestBody Map<String, Object> event) {
        notifications.add(event);
        System.out.println("Received notification: " + event);
        return ResponseEntity.ok(Map.of(
                "status", "received",
                "count", notifications.size()
        ));
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notifications);
    }
}
