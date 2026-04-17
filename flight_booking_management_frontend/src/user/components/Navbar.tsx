import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="navbar">
      <div>
        <strong>Flight Booking System</strong>
      </div>

      <div className="nav-links">
        {role === "PASSENGER" && <Link to="/passenger/dashboard">Dashboard</Link>}
        {role === "PASSENGER" && <Link to="/passenger/profile">Profile</Link>}
        {role === "ADMIN" && <Link to="/admin/dashboard">Admin Dashboard</Link>}
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
}
