import { useEffect, useState } from "react";
import { getProfile } from "../../api/authApi";
import Sidebar from "../../../shared/layout/Sidebar";
import { useNavigate } from "react-router-dom";

export default function DashboardPage() {
  const [profile, setProfile] = useState<any>(null);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const [form, setForm] = useState({
    from: "",
    to: "",
    date: "",
  });

  useEffect(() => {
    getProfile()
      .then(setProfile)
      .catch(() => {
        setError("Failed to load profile");
      });
  }, []);

  const handleSearch = () => {
    if (!form.from || !form.to || !form.date) {
      alert("Please fill all fields");
      return;
    }

    console.log("SEARCH DATA:", form); // debug

    navigate("/search-results", { state: form });
  };

  if (error) return <p className="p-6 text-red-500">{error}</p>;
  if (!profile) return <p className="p-6">Loading...</p>;

  return (
    <div className="min-h-screen flex bg-gradient-to-br from-sky-100 via-blue-200 to-indigo-300 relative overflow-hidden">

      {/* Glow */}
      <div className="absolute top-[-100px] left-[20%] w-[500px] h-[500px] bg-white opacity-20 rounded-full blur-3xl pointer-events-none"></div>
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,_rgba(255,255,255,0.4),transparent_60%)] pointer-events-none"></div>

      <Sidebar />

      <div className="flex-1 p-6 relative z-10">

        <h1 className="text-4xl font-bold mb-2">
          Welcome back, <span className="text-blue-600">{profile?.fullName}</span> ✈️
        </h1>

        <p className="text-gray-600 mb-6">
          Where will you fly today?
        </p>

        {/* SEARCH BOX */}
        <div className="bg-white/70 backdrop-blur-lg border border-white/30 p-6 rounded-2xl shadow-lg mb-6">

          <h2 className="text-xl font-semibold mb-4">✈️ Search Flights</h2>

          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">

            <input
              type="text"
              placeholder="From (City)"
              value={form.from}
              onChange={(e) => setForm({ ...form, from: e.target.value })}
              className="p-3 rounded-lg border focus:ring-2 focus:ring-blue-400"
            />

            <input
              type="text"
              placeholder="To (City)"
              value={form.to}
              onChange={(e) => setForm({ ...form, to: e.target.value })}
              className="p-3 rounded-lg border focus:ring-2 focus:ring-blue-400"
            />

            <input
              type="date"
              value={form.date}
              onChange={(e) => setForm({ ...form, date: e.target.value })}
              className="p-3 rounded-lg border focus:ring-2 focus:ring-blue-400"
            />

            <select className="p-3 rounded-lg border">
              <option>1 Passenger</option>
              <option>2 Passengers</option>
            </select>
          </div>

          <button
            onClick={handleSearch}
            className="mt-5 w-full bg-gradient-to-r from-blue-500 to-indigo-600 text-white py-3 rounded-lg font-semibold hover:shadow-xl transition"
          >
            🔍 Search Flights
          </button>
        </div>

      </div>
    </div>
  );
}