##   ✈️ Flight Booking Management System (FBMS)

The **Flight Booking Management System (FBMS)** is a **distributed, microservices and event-driven-based airline reservation platform** designed to replicate real-world flight booking workflows with a strong focus on **scalability, consistency, and fault tolerance**.


<p align="center">
<img src="https://img.shields.io/badge/Architecture-Microservices%20%2B%20Event--Driven-blue?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Backend-Spring%20Boot-green?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Event%20Driven-Kafka-black?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Security-JWT-orange?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Discovery-Eureka-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Frontend- TypeScript + React-blueviolet?style=for-the-badge"/>
</p>

---

The system enables passengers to seamlessly:
- 🔍 Search flights based on source, destination, and travel date  
- 🎟️ Select seats using a real-time seat map  
- 📖 Create and manage bookings  
- 💳 Complete secure payment transactions  
- 📩 Receive instant booking confirmations and notifications  

---

### 🧩 Problem it Solves

In real-world booking systems, challenges such as **concurrent seat selection, payment failures, and system scalability** can lead to inconsistent data (e.g., double booking).

FBMS addresses these challenges by implementing:
- 🔒 **Seat Locking Mechanism** to prevent double booking  
- 🔄 **Booking Lifecycle Management** for controlled state transitions  
- ⚡ **Event-Driven Communication (Kafka)** for asynchronous processing  
- 🔐 **JWT Authentication** for secure and stateless interactions  

---
### 🏗️ How the System Works (High-Level Flow)

The system supports both **Passenger Operations** and **Admin Operations**, ensuring complete control over flight inventory and booking workflows.

---

### 👨‍✈️ Admin Flow (System Management)

1. Admin logs in with privileged access  
2. Admin can:
   - ✈️ Add new aircrafts (seat configuration, capacity)
   - 🛫 Create flights and schedules  
   - 🛠️ Update or delete flights (CRUD operations)  
3. When a flight is created:
   - Aircraft is mapped to the flight  
   - Seat map is generated dynamically  
4. These flights become immediately available in the system  
5. Users can search and view these flights in real-time  

💡 Admin acts as the **source of flight inventory data**

---

### 👤 Passenger Flow (Booking Journey)

1. User registers / logs in  
2. Searches for available flights  
3. System fetches data from **Flight Inventory Service**  
4. User selects a flight and views seat map  
5. User selects seat → initiates booking  

---

### 🔄 Booking & Payment Flow

6. Booking Service creates booking (`REQUESTED`)  
7. Booking Service calls Flight Inventory → reserve seat  
8. Flight Inventory:
   - If AVAILABLE → mark RESERVED  
   - Else → reject request  

9. Payment Service processes transaction  

---

### ✅ Success Scenario

10. Payment successful →  
   - Booking → `CONFIRMED`  
   - Seat → `OCCUPIED`  
   - Event published (`BookingConfirmedEvent`)  

11. Notification Service:
   - Sends Email/SMS confirmation  

---

### ❌ Failure Scenario

- Payment fails →  
  - Booking → `CANCELLED`  
  - Seat → RELEASED  

---

### 🔁 Continuous System Behavior

- Admin can continuously:
  - Add/update flights  
  - Manage aircrafts  
- Users always see **latest available flights and seats**  
- Kafka ensures background processes (notifications) do not block user flow  

---

### 🎯 Key Takeaway

The system ensures:
- 🔒 Consistent seat handling  
- 🔄 Controlled booking lifecycle  
- ⚡ Real-time flight availability  
- 🧩 Clear separation between Admin and User responsibilities  

---

### 🧠 Architectural Approach

The system is built using:

- **Microservices Architecture (MSA)**  
  Each domain (User, Flight, Booking, Payment, Notification) is implemented as an independent service.

- **Domain-Driven Design (DDD)**  
  Business logic is divided into bounded contexts ensuring clear separation of concerns.

- **Event-Driven Architecture (EDA)**  
  Kafka is used for asynchronous communication, enabling loose coupling and high scalability.

- **API Gateway Pattern**  
  A centralized gateway handles routing, authentication, and request filtering.

- **Service Discovery (Eureka)**  
  Enables dynamic service registration and load balancing.

---

## 🏗️ System Architecture

```mermaid
graph TD
    Client --> Gateway(API Gateway)

    Gateway --> UserService
    Gateway --> FlightInventoryService
    Gateway --> BookingService
    Gateway --> PaymentService
    Gateway --> NotificationService

    subgraph Infrastructure
        Eureka
        Kafka
        PostgreSQL
    end

    BookingService --> FlightInventoryService
    BookingService --> PaymentService
    BookingService --> Kafka
    Kafka --> NotificationService
