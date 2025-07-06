package com.dngi.meeting_reservation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dngi.meeting_reservation.model.User;
import com.dngi.meeting_reservation.service.Scheduler;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Scheduler scheduler;

    public UserController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        scheduler.registerUser(user);
        return ResponseEntity.ok("User Registered successfully with ID: " + user.getId());
    }

    @PostMapping("/{userId}/addAvailability")
    public ResponseEntity<String> addAvailability(@PathVariable String userId, @RequestBody String slot) {
        try {
            LocalDateTime slotTime = LocalDateTime.parse(slot);
            User user = scheduler.getUserById(userId);
            if (user != null) {
                user.addAvailability(slotTime);
                return ResponseEntity.ok("Availability added!");
            } else {
                return ResponseEntity.badRequest().body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/meetings")
    public ResponseEntity<?> listUserMeetings(@PathVariable String userId) {
        try {
            List<?> meetings = scheduler.listUserMeetings(userId);
            return ResponseEntity.ok(meetings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
