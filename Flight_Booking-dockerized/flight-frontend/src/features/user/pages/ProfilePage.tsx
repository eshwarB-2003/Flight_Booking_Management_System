import { useEffect, useState } from "react";
import api from "../../../shared/services/api";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../../shared/layout/Sidebar";

interface User {
  fullName: string;
  email: string;
  phoneNumber: string;
  role: string;
}

export default function ProfilePage() {
  const navigate = useNavigate();
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const res = await api.get("/api/users/profile");
      setUser(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  if (!user) return <p className="p-6">Loading...</p>;

  return (
  <div className="flex min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-100 relative overflow-hidden">

      {/* Sidebar */}
      <Sidebar />

      {/* Main Content */}
      <div className="flex-1 p-6">

        {/* Header Row */}
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">Profile</h2>

          <button
            onClick={() => navigate("/dashboard")}
            className="bg-gray-800 text-white px-4 py-2 rounded hover:bg-black"
          >
            ← Dashboard
          </button>
        </div>

        {/* Profile Card */}
        <div className="max-w-2xl bg-white shadow rounded-xl p-6">
          <div className="space-y-3">
            <p><b>Full Name:</b> {user.fullName}</p>
            <p><b>Email:</b> {user.email}</p>
            <p><b>Phone:</b> {user.phoneNumber}</p>
            <p><b>Role:</b> {user.role}</p>
          </div>

          <div className="mt-6 flex gap-4">
            <button
              onClick={() => navigate("/edit-profile")}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              Edit Profile
            </button>

            <button
              onClick={() => navigate("/change-password")}
              className="bg-gray-800 text-white px-4 py-2 rounded"
            >
              Change Password
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}