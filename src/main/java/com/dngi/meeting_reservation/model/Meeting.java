package com.dngi.meeting_reservation.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Meeting {
    private String id;
    private User host;
    private User guest;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Meeting() {
        this.id = null;
        this.host = null;
        this.guest = null;
        this.startTime = null;
        this.endTime = null;
    }

    public Meeting(User host, User guest, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.host = host;
        this.guest = guest;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Meeting(String id, User host, User guest, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getHost() {
        return host;
    }

    public User getGuest() {
        return guest;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean conflictsWith(Meeting other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        return "Meeting{id='" + id + "', host=" + host.getName() + ", guest=" + guest.getName() +
                ", start=" + startTime + ", end=" + endTime + "}";
    }
}