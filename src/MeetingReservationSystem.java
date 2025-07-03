import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.User;
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
            if (user != null) {
                user.addAvailability(slot);
                scheduler.saveUsersToFile(USERS_PATH);
                System.out.println("Availability added!");
            } else {
                System.out.println("User not found");
            }
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

        try {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

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