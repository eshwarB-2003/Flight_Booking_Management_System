import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";

export default function PassengerDashboard() {
  return (
    <>
      <Navbar />
      <div className="container">
        <h1>Passenger Dashboard</h1>
        <p>Welcome to the passenger area.</p>

        <div className="dashboard-links">
          <Link to="/passenger/profile" className="card-link">
            View and Update Profile
          </Link>
        </div>
      </div>
    </>
  );
}

