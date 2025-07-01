package service;

import model.Meeting;
import model.User;

import java.time.LocalDateTime;
import java.util.*;

public class Scheduler {
    private Map<String, User> users;
    private List<Meeting> meetings;

    public Scheduler() {
        this.users = new HashMap<>();
        this.meetings = new ArrayList<>();
    }

    public void registerUser(User user) {
        users.putIfAbsent(user.getId(), user);
    }

    public boolean scheduleMeeting(String hostId, String guestId, LocalDateTime startTime, LocalDateTime endTime) {
        User host = users.get(hostId);
        User guest = users.get(guestId);

        if (host == null || guest == null) {
            System.out.println("User not found");
            return false;
        }

        if (!host.isAvailable(startTime) || !guest.isAvailable(startTime)) {
            System.out.println("One of the users is not available");
            return false;
        }

        Meeting newMeeting = new Meeting(UUID.randomUUID().toString(), host, guest, startTime, endTime);
        for(Meeting meeting : meetings) {
            if (meeting.conflictsWith(newMeeting)) {
                System.out.println("Meeting time conflicts with existing meeting");
                return false;
            }
        }

        meetings.add(newMeeting);
        host.removeAvailability(startTime);
        guest.removeAvailability(startTime);
        System.out.println("Meeting created successfully");
        return true;
    }
}