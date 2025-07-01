package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private List<LocalDateTime> availableSlots;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.availableSlots = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<LocalDateTime> getAvailableSlots() {
        return availableSlots;
    }

    public void addAvailability(LocalDateTime slot) {
        if (!availableSlots.contains(slot)) {
            availableSlots.add(slot);
        }
    }

    public void removeAvailability(LocalDateTime slot) {
        if (!availableSlots.contains(slot)) return;

        availableSlots.remove(slot);
    }
}