export default function SeatLegend() {
  return (
    <div style={container}>
      <div style={item}>
        <div style={{ ...box, backgroundColor: "green" }} />
        <span>Available</span>
      </div>

      <div style={item}>
        <div style={{ ...box, backgroundColor: "orange" }} />
        <span>Locked</span>
      </div>

      <div style={item}>
        <div style={{ ...box, backgroundColor: "red" }} />
        <span>Booked</span>
      </div>
    </div>
  );
}

/* 🎨 STYLES */

const container = {
  display: "flex",
  justifyContent: "center",
  gap: "25px",
  marginTop: "20px",
};

const item = {
  display: "flex",
  alignItems: "center",
  gap: "8px",
  color: "white",
  fontSize: "14px",
};

const box = {
  width: "18px",
  height: "18px",
  borderRadius: "4px",
};