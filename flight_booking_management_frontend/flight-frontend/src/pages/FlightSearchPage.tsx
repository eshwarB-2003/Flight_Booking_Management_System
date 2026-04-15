import { useState } from "react";
import { api } from "../api/api";
import SearchForm from "../components/SearchForm";
import FlightCard from "../components/FlightCard";
import SeatGrid from "../components/SeatGrid";
import SeatLegend from "../components/SeatLegend";

export default function FlightSearchPage() {
  const [flights, setFlights] = useState<any[]>([]);
  const [seats, setSeats] = useState<any[]>([]);
  const [selectedSchedule, setSelectedSchedule] = useState<number | null>(null);

  const searchFlights = async (from: string, to: string, date: string) => {
    const res = await api.searchFlights(from, to, date);
    setFlights(res);
  };

  const loadSeats = async (scheduleId: number) => {
    const res = await api.getSeats(scheduleId);
    setSeats(res);
    setSelectedSchedule(scheduleId);
  };

  const handleSeatClick = async (seat: any) => {
    const status = (seat.seatStatus || "").toUpperCase();

    if (!selectedSchedule) return;

    if (status === "BOOKED") {
      alert("Seat already booked");
      return;
    }

    if (status === "AVAILABLE") {
      await api.lockSeat(selectedSchedule, seat.seatNumber);
      alert("Seat locked ");
    } else if (status === "LOCKED") {
      await api.bookSeat(selectedSchedule, seat.seatNumber);
      alert("Seat booked ");
    }

    await loadSeats(selectedSchedule);
  };

 return (
  <div
    style={{
      textAlign: "center",
      marginTop: "60px",
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    }}
  >
      <h1>Flight Search</h1>

      <SearchForm onSearch={searchFlights} />
{/*  */}
      <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: "10px",
        marginTop: "30px",
      }}
    >
      {flights.map((f) => (
        <FlightCard
          key={f.scheduleId}
          flight={f}
          onClick={loadSeats}
          selected={selectedSchedule === f.scheduleId}
        />
      ))}
    </div>

    {/* SEATS */}
    {selectedSchedule && (
      <>
        <h2>Select Seat</h2>
         <SeatLegend />
        <SeatGrid
  seats={seats}
  onSeatClick={handleSeatClick}
  reloadSeats={() => loadSeats(selectedSchedule)}
/>
      </>
    )}
  </div>
);
}

