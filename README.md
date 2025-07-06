# 1:1 Meeting Reservation System

A robust Java-based Spring Boot REST API for scheduling and managing 1:1 meetings, designed with extensibility and clean architecture in mind. This project demonstrates strong object-oriented design, conflict management, and user-centric scheduling logic.

---

## 🚀 Features

- **User Registration:** Register users with unique IDs and emails via REST API.
- **Availability Management:** Users can add available time slots via REST API.
- **Smart Scheduling:** Schedule meetings only when both host and guest are available, with automatic conflict detection.
- **Meeting Management:** Cancel meetings and automatically restore user availability.
- **View Meetings:** List all meetings for a specific user.
- **REST API:** Interact with the system using HTTP endpoints (Postman, curl, etc).
- **Extensible Design:** Easily add persistence, reporting, or a GUI.

---

## 🧑‍💻 Usage

1. **Build & Run**
   - Build and run with Maven or your favorite IDE:
     ```sh
     ./mvnw spring-boot:run
     ```
   - The API will be available at `http://localhost:8080/`

2. **API Endpoints**

### Register User
- **POST** `/users`
- **Body (JSON):**
  ```json
  {
    "name": "Alice",
    "email": "alice@example.com"
  }
  ```
- **Response:**
  `User Registered successfully with ID: <user-id>`

### Add Availability
- **POST** `/users/{userId}/addAvailability`
- **Body (raw text):**
  ```
  2025-07-07T10:00:00
  ```
- **Response:**
  `Availability added!`

### List User Meetings
- **GET** `/users/{userId}/meetings`
- **Response:**
  JSON array of meetings

### Schedule Meeting
- **POST** `/meetings`
- **Body (JSON):**
  ```json
  {
    "hostId": "<host-user-id>",
    "guestId": "<guest-user-id>",
    "startTime": "2025-07-07T10:00:00",
    "end": "2025-07-07T11:00:00"
  }
  ```
- **Response:**
  `Meeting scheduled successfully!`

### Cancel Meeting
- **DELETE** `/meetings/{meetingId}`
- **Response:**
  `Meeting cancelled!`

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot**
- **Standard Library Only** (no external dependencies except Spring Boot)

---

## 📈 Extensibility

- **Persistence:** Easily add file or database storage (see `saveUsersToFile` in `service.Scheduler`).
- **GUI:** Upgrade to JavaFX or a web frontend.
- **Reporting:** Add analytics on meetings per user.
- **Authentication:** Integrate user login for security.

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
