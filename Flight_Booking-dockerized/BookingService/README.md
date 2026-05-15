# Booking Service

This service follows:
* Domain Driven Design (DDD)
* State Pattern (Booking lifecycle)
* Factory Pattern (Booking creation)
* Event-driven architecture (Kafka-ready)

---

# Base URL

```
http://localhost:8082/api/bookings
```

---

# Booking APIs

## 1. Create Booking

**POST** `/api/bookings`

Creates a new booking and locks the selected seat.

### Request

```json
{
  "userId": 1,
  "flightId": 101,
  "scheduleId": 10,
  "seatNumber": "12A",
  "totalAmount": 300.00
}
```

### Response

```json
{
  "bookingId": 1,
  "bookingReference": "FLY-AB123",
  "bookingStatus": "REQUESTED",
  "totalAmount": 300.00
}
```

---

## 2. Confirm Booking

**PUT** `/api/bookings/{bookingId}/confirm`

Changes booking status to **CONFIRMED** and confirms seat reservation.

### Example

```
PUT /api/bookings/1/confirm
```

Response

```
Booking confirmed
```

---

## 3. Cancel Booking

**PUT** `/api/bookings/{bookingId}/cancel`

Cancels booking and releases seat.

### Example

```
PUT /api/bookings/1/cancel
```

Response

```
Booking cancelled
```

---

## 4. Get Booking by ID

**GET** `/api/bookings/{bookingId}`

### Example

```
GET /api/bookings/1
```

Response

```json
{
  "bookingId": 1,
  "bookingReference": "FLY-AB123",
  "bookingStatus": "CONFIRMED",
  "totalAmount": 300.00
}
```

---

## 5. Get My Bookings

**GET** `/api/bookings/my-bookings/{userId}`

### Example

```
GET /api/bookings/my-bookings/1
```

Response

```json
[
  {
    "bookingId": 1,
    "bookingReference": "FLY-AB123",
    "bookingStatus": "CONFIRMED",
    "totalAmount": 300.00
  }
]
```

---

# Ancillary APIs

## 6. Add Ancillary

**POST** `/api/bookings/{bookingId}/ancillaries`

### Request

```json
{
  "itemId": 10,
  "quantity": 1
}
```

Response

```
Ancillary added
```

---

## 7. Get Ancillaries

**GET** `/api/bookings/{bookingId}/ancillaries`

Example:

```
GET /api/bookings/1/ancillaries
```

---

## 8. Remove Ancillary

**DELETE** `/api/bookings/ancillaries/{id}`

Example:

```
DELETE /api/bookings/ancillaries/5
```

---

# Booking Lifecycle

```
REQUESTED → CONFIRMED
REQUESTED → CANCELLED
```

---

# Seat Reservation Lifecycle

```
LOCKED → CONFIRMED
LOCKED → RELEASED
```

---

# Event Publishing

When booking is confirmed:

```
BookingConfirmedEvent
```

Payload:

```json
{
  "bookingId": 1,
  "userId": 1,
  "flightId": 101
}
```

Used by Notification Service (Kafka consumer)

---

# Design Patterns Used

### State Pattern

* RequestedState
* ConfirmedState
* CancelledState

### Factory Pattern

* BookingFactory

### Observer Pattern

* BookingConfirmedEvent publisher

---

# Database Tables

* booking
* seat_reservation
* ancillary_item
* booking_ancillary

---

# Microservice Integration

Booking Service calls:

FlightInventoryService

```
POST /api/seats/{scheduleId}/lock
```

to lock seats during booking.