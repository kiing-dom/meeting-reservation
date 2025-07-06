package com.dngi.meeting_reservation.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dngi.meeting_reservation.service.Scheduler;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private final Scheduler scheduler;

    public MeetingController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping
    public ResponseEntity<String> scheduleMeeting(@RequestBody Map<String, String> request) {
        String hostId = request.get("hostId");
        String guestId = request.get("guestId");
        String start = request.get("startTime");
        String end = request.get("end");

        try {
            LocalDateTime startTime = LocalDateTime.parse(start);
            LocalDateTime endTime = LocalDateTime.parse(end);

            scheduler.scheduleMeeting(hostId, guestId, startTime, endTime);
            return ResponseEntity.ok("Meeting scheduled successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error scheduling meeting: " + e.getMessage());
        }
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<String> cancelMeeting(@PathVariable String meetingId) {
        scheduler.cancelMeeting(meetingId);
        return ResponseEntity.ok("Meeting cancelled!");

    }
}
