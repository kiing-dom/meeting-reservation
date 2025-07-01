package model;

import java.time.LocalDateTime;

public class Meeting {
    private final String id;
    private User host;
    private User guest;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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
}