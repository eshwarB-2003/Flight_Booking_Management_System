import { useState } from "react";

export default function SearchForm({ onSearch }: any) {
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [date, setDate] = useState("");

  const cities = [
    "Chennai",
    "Dubai",
    "London",
    "New York",
    "Paris",
    "Tokyo",
    "Singapore"
  ];

  return (
    <div style={container}>
      <div style={inputRow}>
        
        {/* FROM */}
        <select
          style={inputStyle}
          value={from}
          onChange={(e) => setFrom(e.target.value)}
        >
          <option value="">From</option>
          {cities.map((city) => (
            <option key={city} value={city}>
              {city}
            </option>
          ))}
        </select>

        {/* TO */}
        <select
          style={inputStyle}
          value={to}
          onChange={(e) => setTo(e.target.value)}
        >
          <option value="">To</option>
          {cities.map((city) => (
            <option key={city} value={city}>
              {city}
            </option>
          ))}
        </select>

        <input
          style={inputStyle}
          type="date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
      </div>

      <button
        style={buttonStyle}
        onClick={() => onSearch(from, to, date)}
      >
         Search Flights
      </button>
    </div>
  );
}



const container = {
  display: "flex",
  flexDirection: "column" as const,
  alignItems: "center",
  gap: "15px",
  marginTop: "20px",
};


const inputRow = {
  display: "flex",
  flexDirection: "column" as const,
  gap: "12px",
  width: "300px", 
};
const inputStyle = {
  padding: "12px",
  borderRadius: "8px",
  border: "1px solid #ccc",
  minWidth: "150px",
  fontSize: "14px",
};

const buttonStyle = {
  padding: "12px 25px",
  backgroundColor: "#007bff",
  color: "white",
  border: "none",
  borderRadius: "8px",
  cursor: "pointer",
  fontSize: "16px",
};