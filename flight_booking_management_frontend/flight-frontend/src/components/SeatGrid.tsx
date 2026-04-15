import { useEffect, useState } from "react";

export default function SeatGrid({ seats, onSeatClick, reloadSeats }: any) {
  const [timers, setTimers] = useState<any>({});

  useEffect(() => {
    const interval = setInterval(() => {
      const updatedTimers: any = {};
      let shouldRefresh = false;

      seats.forEach((seat: any) => {
        if (seat.lockTime) {
          const lockTime = new Date(seat.lockTime).getTime();
          const now = new Date().getTime();

          const LOCK_DURATION = 300000;
          const diff = LOCK_DURATION - (now - lockTime);
          const seconds = Math.max(0, Math.floor(diff / 1000));

          updatedTimers[seat.seatNumber] = seconds;

          // ✅ trigger refresh when timer ends
          if (seconds === 0) {
            shouldRefresh = true;
          }
        }
      });

      setTimers(updatedTimers);

      // ✅ auto refresh seats
      if (shouldRefresh && reloadSeats) {
        reloadSeats();
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [seats]);

  const sortedSeats = [...seats].sort((a, b) => {
    const rowA = parseInt(a.seatNumber);
    const rowB = parseInt(b.seatNumber);

    if (rowA !== rowB) return rowA - rowB;

    const colA = a.seatNumber.replace(/[0-9]/g, "");
    const colB = b.seatNumber.replace(/[0-9]/g, "");

    return colA.localeCompare(colB);
  });

  return (
    <div
      style={{
        display: "grid",
        gridTemplateColumns: "repeat(6, 70px)",
        gap: "12px",
        justifyContent: "center",
        marginTop: "20px",
      }}
    >
      {sortedSeats.map((seat: any) => {
        const status = (seat.seatStatus || "").toUpperCase();
        const timer = timers[seat.seatNumber];

        return (
          <div key={seat.seatNumber} style={{ textAlign: "center" }}>
            <button
              disabled={status === "BOOKED" || status === "OCCUPIED"}
              onClick={() => onSeatClick(seat)}
              style={{
                width: "60px",
                height: "50px",
                borderRadius: "6px",
                border: "none",
                cursor: "pointer",
                backgroundColor:
                  status === "BOOKED" || status === "OCCUPIED"
                    ? "red"
                    : status === "LOCKED"
                    ? "orange"
                    : "green",
                color: "white",
                fontWeight: "bold",
              }}
            >
              {seat.seatNumber}
            </button>

            {/* TIMER */}
            {status === "LOCKED" && timer > 0 && (
              <div style={{ fontSize: "12px", color: "orange" }}>
                {timer}s
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}