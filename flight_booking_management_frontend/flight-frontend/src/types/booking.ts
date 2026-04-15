export interface SeatReservation {
  reservationId: number;
  seatNumber: string;
  reservationStatus: string;
}

export interface Booking {
  bookingId: number;
  bookingReference: string;
  userId: number;
  flightId: number;
  bookingStatus: "REQUESTED" | "CONFIRMED" | "CANCELLED";
  totalAmount: number;
  seatReservations: SeatReservation[];
}