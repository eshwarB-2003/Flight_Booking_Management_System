import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import apiClient from "../../services/apiClient";
import { API_ENDPOINTS } from "../../services/apiEndpoints";
import type { Booking } from "../../types/booking";

const BookingSummary = () => {
  const { bookingId } = useParams();
  const navigate = useNavigate();

  const [booking, setBooking] = useState<Booking | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBooking = async () => {
      try {
        const res = await apiClient.get(
          API_ENDPOINTS.BOOKING.GET_BY_ID(Number(bookingId))
        );

        setBooking(res.data);
      } catch (err) {
        console.error("Error fetching booking", err);
      } finally {
        setLoading(false);
      }
    };

    if (bookingId) fetchBooking();
  }, [bookingId]);

  if (loading) return <p>Loading booking...</p>;
  if (!booking) return <p>Booking not found</p>;

  return (
    <div className="card">
      <h2>Booking Summary</h2>

      <p><b>Reference:</b> {booking.bookingReference}</p>
      <p><b>Status:</b> {booking.bookingStatus}</p>
      <p><b>Total:</b> ${booking.totalAmount}</p>

      <h3>Seats</h3>
      {booking.seatReservations.map((seat) => (
        <p key={seat.reservationId}>
          {seat.seatNumber} ({seat.reservationStatus})
        </p>
      ))}

      <button
        className="btn"
        onClick={() => navigate(`/booking/${booking.bookingId}/ancillary`)}
      >
        Add Services
      </button>
    </div>
  );
};

export default BookingSummary;