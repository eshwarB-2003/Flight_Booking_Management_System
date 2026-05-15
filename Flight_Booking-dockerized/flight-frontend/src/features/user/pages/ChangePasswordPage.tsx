import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../../shared/services/api";

export default function ChangePasswordPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    currentPassword: "",
    newPassword: "",
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!form.currentPassword || !form.newPassword) {
      alert("All fields are required");
      return;
    }

    if (form.newPassword.length < 6) {
      alert("New password must be at least 6 characters");
      return;
    }

    try {
      setLoading(true);

      const res = await api.put("/api/users/change-password", form);

      alert(res.data.message || "Password changed successfully ✅");

      // ✅ Redirect back to dashboard
      navigate("/dashboard", { replace: true });

    } catch (err: any) {
      console.error(err);
      alert(err.response?.data?.message || "Failed to change password ❌");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6 flex items-center justify-center">
      <div className="w-full max-w-md bg-white shadow rounded-xl p-6">
        <h2 className="text-2xl font-bold mb-6 text-center">
          🔐 Change Password
        </h2>

        <div className="space-y-4">
          <input
            type="password"
            name="currentPassword"
            placeholder="Current Password"
            value={form.currentPassword}
            onChange={handleChange}
            className="w-full p-3 border rounded"
          />

          <input
            type="password"
            name="newPassword"
            placeholder="New Password"
            value={form.newPassword}
            onChange={handleChange}
            className="w-full p-3 border rounded"
          />

          <button
            onClick={handleSubmit}
            disabled={loading}
            className="w-full bg-gray-800 text-white p-3 rounded hover:bg-black"
          >
            {loading ? "Updating..." : "Update Password"}
          </button>
        </div>
      </div>
    </div>
  );
}