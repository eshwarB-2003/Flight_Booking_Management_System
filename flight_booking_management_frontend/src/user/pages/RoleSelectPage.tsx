import { useNavigate } from "react-router-dom";

export default function RoleSelectPage() {
  const navigate = useNavigate();

  return (
    <div className="container">
      <h1>Choose Portal</h1>
      <div className="role-grid">
        <div className="role-card">
          <h2>Passenger</h2>
          <p>Login or register as a passenger.</p>
          <button onClick={() => navigate("/passenger/login")}>Passenger Login</button>
          <button className="secondary-btn" onClick={() => navigate("/passenger/register")}>
            Passenger Register
          </button>
        </div>

        <div className="role-card">
          <h2>Admin</h2>
          <p>Login or register as an admin.</p>
          <button onClick={() => navigate("/admin/login")}>Admin Login</button>
          <button className="secondary-btn" onClick={() => navigate("/admin/register")}>
            Admin Register
          </button>
        </div>
      </div>
    </div>
  );
}