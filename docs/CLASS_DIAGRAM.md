# Class Diagram: 1-1 Meeting Reservation System
## Overview

### User
- `id: String`
-  `name: String`
-  `email: String`
-  `availableSlots: List<LocalDateTime>`

#### Methods:
- `addAvailability(slot: LocalDateTime)`  
- `removeAvailability(slot: LocalDateTime)`  
- `getAvailability(): List<LocalDateTime>`
- `isAvailable(): boolean` 

---

### Meeting
- `id: String` 
- `host: User` 
- `guest: User` 
- `startTime: LocalDateTime` 
- `endTime: LocalDateTime` 

#### Methods:
- `conflictsWith(Meeting other): boolean`

---

### Scheduler
- `users: Map<String, User>`
- `meetings: List<Meeting>`

#### Methods:
- `registerUser(User user)`
- `scheduleMeeting(String hostId, String guestId, LocalDateTime startTime, LocalDateTime endTime): boolean`
- `cancelMeeting(String meetingId): boolean`
- `listUserMeetings(String userId): List<Meeting>`
- `getAvailableSlots(String userId): List<LocalDateTime>`

---
##### TBD
- persistence manager