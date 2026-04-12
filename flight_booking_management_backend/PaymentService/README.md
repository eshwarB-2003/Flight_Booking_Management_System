# Payment Service API Documentation

## Overview

The Payment Service handles payment processing for flight bookings.
It supports multiple payment methods and updates booking status based on payment outcome.

---

## Base URL

```
http://localhost:8083/api/payments
```

---

## 1. Process Payment

### Endpoint

```
POST /process
```

### Description

Processes a payment for a booking and updates its status.

### Request Body

```json
{
  "bookingId": 301,
  "amount": 5000.0,
  "paymentMethod": "CARD"
}
```

### Response

```json
{
  "paymentId": 1,
  "bookingId": 301,
  "amount": 5000.0,
  "paymentStatus": "SUCCESS",
  "paymentMethod": "CARD",
  "transactionReference": "TXN123456",
  "paymentDate": "2026-04-11T17:00:00"
}
```

---

## 2. Get Payment by ID

### Endpoint

```
GET /{paymentId}
```

### Description

Fetch payment details using payment ID.

---

## 3. Get Payments by Booking ID

### Endpoint

```
GET /booking/{bookingId}
```

### Description

Retrieve all payments for a specific booking.

---

## Payment Methods

* CARD
* UPI
* NETBANKING

---

## Payment Status

* SUCCESS
* FAILED

---

## Internal Integration

### Booking Service API Calls

#### Confirm Booking

```
POST http://localhost:8082/api/bookings/{bookingId}/confirm
```

#### Cancel Booking

```
POST http://localhost:8082/api/bookings/{bookingId}/cancel
```

---

## Notes

* Payment success is determined using simulated logic.
* On success → booking is confirmed.
* On failure → booking is cancelled or remains pending.

---
