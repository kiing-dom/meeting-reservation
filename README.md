# 1:1 Meeting Reservation System

A robust Java-based CLI application for scheduling and managing 1:1 meetings, designed with extensibility and clean architecture in mind. This project demonstrates strong object-oriented design, conflict management, and user-centric scheduling logic.

---

## 🚀 Features

- **User Registration:** Register users with unique IDs and emails.
- **Availability Management:** Users can add or remove available time slots.
- **Smart Scheduling:** Schedule meetings only when both host and guest are available, with automatic conflict detection.
- **Meeting Management:** Cancel meetings and automatically restore user availability.
- **View Meetings:** List all meetings for a specific user.
- **CLI Interface:** Intuitive command-line menu for all operations.
- **Extensible Design:** Easily add persistence, reporting, or a GUI.

---

## 🧑‍💻 Usage

1. **Build & Run**
   - Compile with your favorite IDE or via command line:
     ```sh
     javac -d out src/model/*.java src/service/*.java src/MeetingReservationSystem.java
     java -cp out MeetingReservationSystem
     ```

2. **CLI Menu**
   - Register users, add availability, schedule/cancel meetings, and view meetings—all from an interactive menu.

3. **Sample Flow**
   - Register two users.
   - Add available slots for both.
   - Schedule a meeting at a mutually available time.
   - List meetings for a user.

---

## 📝 Example

```
--- 1:1 MEETING RESERVATION SYSTEM ---
1. Register User
2. Add Availability
3. Schedule a Meeting
4. Cancel Meeting
5. List User Meetings
6. Exit
Select an option: 1
Enter user name: Dom
Enter user email: dom@example.com
User Alice registered successfully! User ID: 123e4567-e89b-12d3-a456-426614174000
```

---

## 🛠️ Tech Stack

- **Java 17+**
- **Standard Library Only** (no external dependencies) (rn at least)

---

## 📈 Extensibility

- **Persistence:** Easily add file or database storage (see `saveUsersToFile` in `service.Scheduler`).
- **GUI:** Upgrade to JavaFX or a web frontend.
- **Reporting:** Add analytics on meetings per user.
- **Authentication:** Integrate user login for security.
- **Spring Boot:** Might use Spring Boot to further enhance
---

## 📚 Documentation

- [Class Diagram](docs/CLASS_DIAGRAM.md)
- [Milestones & Roadmap](docs/MILESTONES.md)

---

## 🤝 Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements or new features.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 👤 Author

Built with care by me (dom/kiing-dom).  
[LinkedIn](https://www.linkedin.com/in/dominion-gbadamosi)
