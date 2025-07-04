package service;

import model.Meeting;
import model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        for (Meeting meeting : meetings) {
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

    public boolean cancelMeeting(String meetingId) {
        Iterator<Meeting> iterator = meetings.iterator();

        while (iterator.hasNext()) {
            Meeting meeting = iterator.next();
            if (meeting.getId().equals(meetingId)) {
                meeting.getHost().addAvailability(meeting.getStartTime());
                meeting.getGuest().addAvailability(meeting.getStartTime());
                iterator.remove();
                System.out.println("Meeting cancelled.");
                return true;
            }
        }

        System.out.println("Meeing not found!");
        return false;
    }

    public List<Meeting> listUserMeetings(String userId) throws IllegalArgumentException {
        if (userId == null || userId.isEmpty() || !users.containsKey(userId)) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }

        List<Meeting> userMeetings = new ArrayList<>();
        User user = users.get(userId);
        for (Meeting meeting : meetings) {
            if (meeting.getHost().equals(user) || meeting.getGuest().equals(user)) {
                userMeetings.add(meeting);
            }
        }

        return userMeetings;
    }

    public List<LocalDateTime> getAvailableSlots(String userId) {
        User user = users.get(userId);

        if (user != null) {
            return user.getAvailableSlots();
        }

        return new ArrayList<>();
    }

    public User getUserById(String userId) throws IllegalArgumentException {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("No user ID that matches" + userId);
        }

        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Meeting getMeetingById(String meetingId) {
        for (Meeting meeting : meetings) {
            if (meeting.getId().equals(meetingId)) {
                return meeting;
            }
        }

        throw new IllegalArgumentException("The meeting ID isn't valid");
    }

    public void saveUsersToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(user.getId()).append(",");
                sb.append(user.getName()).append(",");
                sb.append(user.getEmail()).append(",");

                List<LocalDateTime> availability = user.getAvailableSlots();
                for (int i = 0; i < availability.size(); i++) {
                    sb.append(availability.get(i));
                    if (i < availability.size() - 1) {
                        sb.append(",");
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }

            System.out.println("Users saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length >= 3) {
                    User user = new User(parts[0], parts[1], parts[2]);
                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        String[] slots = parts[3].split(";");
                        for (String slot : slots) {
                            user.addAvailability(LocalDateTime.parse(slot));
                        }
                    }

                    users.put(user.getId(), user);
                }
            }

            System.out.println("Users loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void saveMeetingsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Meeting meeting : meetings) {
                writer.write(meeting.getId() + "," +
                        meeting.getHost().getId() + "," +
                        meeting.getGuest().getId() + "," +
                        meeting.getStartTime() + "," +
                        meeting.getEndTime());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving meetings: " + e.getMessage());
        }
    }

    public void loadMeetingsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    User host = users.get(parts[1]);
                    User guest = users.get(parts[2]);
                    LocalDateTime start = LocalDateTime.parse(parts[3]);
                    LocalDateTime end = LocalDateTime.parse(parts[4]);

                    if (host != null && guest != null) {
                        Meeting meeting = new Meeting(id, host, guest, start, end);
                        meetings.add(meeting);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading meetings: " + e.getMessage());
        }
    }

}