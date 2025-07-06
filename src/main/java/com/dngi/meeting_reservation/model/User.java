package com.dngi.meeting_reservation.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private List<LocalDateTime> availableSlots;

    public User() {
        this.availableSlots = new ArrayList<>();
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.availableSlots = new ArrayList<>();
    }

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.availableSlots = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isAvailable(LocalDateTime slot) {
        return availableSlots.contains(slot);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name +"', email='" + email + "', availability='" + availableSlots + "'}";
    }

    // TODO: override equals and hashcode to prioritise userId
}