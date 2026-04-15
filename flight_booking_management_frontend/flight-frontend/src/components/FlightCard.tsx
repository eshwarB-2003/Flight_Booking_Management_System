export default function FlightCard({ flight, onClick, selected }: any) {
  return (
    <div
      onClick={() => onClick(flight.scheduleId)}
      style={{
        width: "520px",
        padding: "20px",
        borderRadius: "12px",
        background: "#1e1e2f",
        border: selected ? "2px solid #00c6ff" : "1px solid #333",
        cursor: "pointer",
        color: "white",
        boxShadow: "0 4px 15px rgba(0,0,0,0.3)",
        marginBottom: "15px",
      }}
    >
      {/* ROUTE */}
      <div style={{ fontSize: "20px", fontWeight: "bold" }}>
        {flight.departureCity} → {flight.destination}
      </div>

      {/* AIRLINE */}
      <div style={{ color: "#aaa", marginBottom: "10px" }}>
        ✈️ {flight.airline} • {flight.flightNumber}
      </div>

      {/* TIME SECTION */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginTop: "10px",
        }}
      >
        {/* DEPARTURE */}
        <div>
          <div style={{ fontSize: "14px", color: "#aaa" }}>Departure</div>
          <div style={{ fontSize: "18px", fontWeight: "bold" }}>
            {new Date(flight.departureTime).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            })}
          </div>
          <div style={{ fontSize: "12px" }}>
            {new Date(flight.departureTime).toLocaleDateString()}
          </div>
        </div>

        {/* ARROW */}
        <div style={{ alignSelf: "center", fontSize: "20px" }}>✈️</div>

        {/* ARRIVAL */}
        <div>
          <div style={{ fontSize: "14px", color: "#aaa" }}>Arrival</div>
          <div style={{ fontSize: "18px", fontWeight: "bold" }}>
            {flight.arrivalTime
              ? new Date(flight.arrivalTime).toLocaleTimeString([], {
                  hour: "2-digit",
                  minute: "2-digit",
                })
              : "--"}
          </div>
          <div style={{ fontSize: "12px" }}>
            {flight.arrivalTime
              ? new Date(flight.arrivalTime).toLocaleDateString()
              : "--"}
          </div>
        </div>
      </div>
    </div>
  );
}