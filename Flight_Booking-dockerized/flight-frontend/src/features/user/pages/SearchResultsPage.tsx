import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import Sidebar from "../../../shared/layout/Sidebar";
import api from "../../../shared/services/api";

export default function SearchResultsPage() {
  const location = useLocation();
  const { from, to, date } = location.state || {};

  const [flights, setFlights] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!from || !to || !date) {
      console.warn("❌ Missing search params:", { from, to, date });
      setLoading(false);
      return;
    }

    const fetchFlights = async () => {
      try {
        const token = localStorage.getItem("token");

        console.log("📤 REQUEST DATA:", { from, to, date });
        console.log("🔐 TOKEN:", token);

        if (!token) {
          console.error("❌ No token found. User might not be logged in.");
          setLoading(false);
          return;
        }

        const res = await api.get("/api/schedules/search", {
          params: { from, to, date },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        console.log("✅ API RESPONSE:", res.data);

        if (!res.data || res.data.length === 0) {
          console.warn("⚠️ No flights returned from backend");
        }

        setFlights(res.data);

      } catch (err: any) {
        console.error("🔥 ERROR OBJECT:", err);

        if (err.response) {
          console.error("🔥 STATUS:", err.response.status);
          console.error("🔥 HEADERS:", err.response.headers);
          console.error("🔥 DATA:", err.response.data);

          if (err.response.status === 401) {
            console.error("🚫 Unauthorized → Token issue");
          } else if (err.response.status === 500) {
            console.error("💥 Server error → Check backend logs");
          }

        } else if (err.request) {
          console.error("🌐 No response received:", err.request);
        } else {
          console.error("⚠️ Error message:", err.message);
        }

      } finally {
        setLoading(false);
      }
    };

    fetchFlights();
  }, [from, to, date]);

  if (!from || !to || !date) {
    return <p className="p-6">Invalid search. Please try again.</p>;
  }

  return (
    <div className="min-h-screen flex bg-gradient-to-br from-sky-100 via-blue-200 to-indigo-300">
      <Sidebar />

      <div className="flex-1 p-6">
        <h1 className="text-3xl font-bold mb-6">
          ✈️ Flights from {from} → {to}
        </h1>

        {loading ? (
          <p>Loading flights...</p>
        ) : flights.length === 0 ? (
          <p>No flights found</p>
        ) : (
          <div className="space-y-4">
            {flights.map((f: any) => (
              <div
                key={f.scheduleId}
                className="bg-white p-5 rounded-xl shadow flex justify-between items-center hover:shadow-lg transition"
              >
                <div>
                  <p className="font-semibold text-lg">{f.airline}</p>

                  <p className="text-sm text-gray-500">
                    {f.departureCity} → {f.destination}
                  </p>

                  <p className="text-sm">
                    {new Date(f.departureTime).toLocaleTimeString([], {
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                    {" → "}
                    {new Date(f.arrivalTime).toLocaleTimeString([], {
                      hour: "2-digit",
                      minute: "2-digit",
                    })}
                  </p>
                </div>

                <div className="text-right">
                  <p className="text-xl font-bold text-blue-600">
                    €{f.price ?? 250}
                  </p>

                  <button className="mt-2 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition">
                    Book
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}