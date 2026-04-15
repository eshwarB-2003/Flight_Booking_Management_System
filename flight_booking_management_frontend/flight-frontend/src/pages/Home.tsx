import { useNavigate } from "react-router-dom";
import { ROUTES } from "../routes/routeConstants";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="card">
      <h1>✈️ Flight Booking System</h1>
      <p>Book flights easily and manage your travel.</p>

      <div style={{ marginTop: "20px" }}>
        {/* Passenger Flow */}
        <button
          className="btn"
          onClick={() => navigate(ROUTES.SEARCH_FLIGHTS)}
        >
          Start Booking
        </button>
      </div>

      {/* 
        PURPOSE:
        - Entry point to the system
        - No business logic here
        - Only navigation

        FLOW:
        Home → SearchFlights → Results → Seats → Booking → Ancillary
      */}
    </div>
  );
};

export default Home;