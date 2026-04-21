import { useAuthStore } from "../../store/authStore";
import { useNavigate, useLocation } from "react-router-dom";

export default function Sidebar() {
  const logout = useAuthStore((s) => s.logout);
  const navigate = useNavigate();
  const location = useLocation();

  // helper for active link
  const isActive = (path: string) => location.pathname === path;

  return (
    
    <div className="w-64 h-screen bg-white shadow-md p-5 flex flex-col justify-between">
      
      {/* Top */}
      <div>
        <h2 className="text-xl font-bold mb-6">✈️ Flight System</h2>

        <nav className="space-y-2">

          <button
            onClick={() => navigate("/dashboard")}
            className={`w-full text-left px-3 py-2 rounded-lg transition 
              ${isActive("/dashboard") ? "bg-blue-500 text-white" : "hover:bg-blue-100"}`}
          >
            🏠 Dashboard
          </button>

          <button
            onClick={() => navigate("/profile")}
            className={`w-full text-left px-3 py-2 rounded-lg transition 
              ${isActive("/profile") ? "bg-blue-500 text-white" : "hover:bg-blue-100"}`}
          >
            👤 Profile
          </button>

          <button
            onClick={() => navigate("/bookings")}
            className="w-full text-left px-3 py-2 rounded-lg hover:bg-blue-100 transition"
          >
            📖 Bookings
          </button>

        </nav>
      </div>

      {/* Bottom */}
      <div>
        <button
          onClick={logout}
          className="w-full text-left px-3 py-2 rounded-lg text-red-500 hover:bg-red-100 transition"
        >
          🚪 Logout
        </button>
      </div>

    </div>
  );
}