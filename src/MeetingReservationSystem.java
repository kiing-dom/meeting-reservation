import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.User;
import model.Meeting;
import service.Scheduler;

public class MeetingReservationSystem {

    private static final Scheduler scheduler = new Scheduler();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String USERS_PATH = "src/db/users.csv";
    private static final String MEETINGS_PATH = "src/db/meetings.csv";

    public static void main(String[] args) {
        scheduler.loadUsersFromFile(USERS_PATH);
        scheduler.loadMeetingsFromFile(MEETINGS_PATH);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> registerUser();
                case "2" -> addAvailability();
                case "3" -> scheduleMeeting();
                case "4" -> cancelMeeting();
                case "5" -> listUserMeetings();
                case "6" -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- 1:1 MEETING RESERVATION SYSTEM ---");
        System.out.println("1. Register User");
        System.out.println("2. Add Availability");
        System.out.println("3. Schedule a Meeting");
        System.out.println("4. Cancel Meeting");
        System.out.println("5. List User Meetings");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private static void registerUser() {
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter user email: ");
        String email = scanner.nextLine();

        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("Name and email cannot be empty");
            return;
        }

        for (User user : scheduler.getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("A user with this email already exists!");
            }
        }

        User user = new User(name, email);
        scheduler.registerUser(user);
        scheduler.saveUsersToFile(USERS_PATH);
        System.out.println("User " + user.getName() + " registered successfully! User ID: " + user.getId());
    }

    private static void addAvailability() {
        System.out.println("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.println("Enter availability slot (yyyy-MM-dd HH:mm): ");
        String slotStr = scanner.nextLine();

        try {
            LocalDateTime slot = LocalDateTime.parse(slotStr, formatter);
            User user = scheduler.getUserById(userId);

            if (user == null) {
                System.out.println("User could not be found.");
                return;
            }

            if (slot.isBefore(LocalDateTime.now())) {
                System.out.println("Cannot create a time slot in the past!");
                return;
            }

            if (user.getAvailableSlots().contains(slot)) {
                System.out.println("User has already reserved this slot!");
                return;
            }

            user.addAvailability(slot);
            scheduler.saveUsersToFile(USERS_PATH);
            System.out.println("Availability added!");

        } catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
    }

    private static void scheduleMeeting() {
        System.out.println("Enter host user ID: ");
        String hostId = scanner.nextLine();
        System.out.println("Enter guest user ID: ");
        String guestId = scanner.nextLine();
        System.out.println("Enter a meeting start time (yyyy-MM-dd HH:mm): ");
        String startTimeStr = scanner.nextLine();
        System.out.println("Enter a meeting end time (yyyy-MM-dd HH:mm): ");
        String endTimeStr = scanner.nextLine();

        if (hostId.equals(guestId)) {
            System.out.println("Host and guest can't be the same");
            return;
        }

        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

            if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
                System.out.println("Start time must be before end time!");
                return;
            }

            if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
                System.out.println("End time must be after start time!");
                return;
            }

            User host = scheduler.getUserById(hostId);
            User guest = scheduler.getUserById(guestId);

            if (host == null || guest == null) {
                System.out.println("Host or guest not found!");
                return;
            }

            if (!host.getAvailableSlots().contains(startTime) || !guest.getAvailableSlots().contains(startTime)) {
                System.out.println("One or both users not available at this time!");
                return;
            }

            
            List<Meeting> userMeetings = scheduler.listUserMeetings(hostId);
            for (Meeting meeting : userMeetings) {
                if (!(endTime.isBefore(meeting.getStartTime()) || startTime.isAfter(endTime))) {
                    System.out.println("Host has a conflicting meeting");
                    return;
                }
            }

            scheduler.scheduleMeeting(hostId, guestId, startTime, endTime);
            scheduler.saveMeetingsToFile(MEETINGS_PATH);
            System.out.println("Scheduled meeting succesfully!");
        } catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
    }

    private static void cancelMeeting() {
        System.out.println("Enter the meeting id: ");
        String meetingId = scanner.nextLine();

        Meeting meetingToCancel = scheduler.getMeetingById(meetingId);
        if (meetingToCancel == null) {
            System.out.println("Meeting not found!");
            return;
        }

        scheduler.cancelMeeting(meetingId);
        scheduler.saveMeetingsToFile(MEETINGS_PATH);
    }

    private static void listUserMeetings() {
        System.out.println("Enter user ID: ");
        String userId = scanner.nextLine();

        try {
            List<?> meetings = scheduler.listUserMeetings(userId);
            if (meetings.isEmpty()) {
                System.out.println("No meetings found!");
            } else {
                System.out.println("Meetings:");
                meetings.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}