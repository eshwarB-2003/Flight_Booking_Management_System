import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../../shared/services/api";

export default function EditProfilePage() {
  const [form, setForm] = useState({
    fullName: "",
    phoneNumber: "",
  });

  const navigate = useNavigate();

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const res = await api.get("/api/users/profile");
      setForm({
        fullName: res.data.fullName,
        phoneNumber: res.data.phoneNumber,
      });
    } catch (err) {
      console.error(err);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      await api.put("/api/users/profile", form);
      alert("Profile updated successfully ");
       navigate("/dashboard", { replace: true }); 
    } catch (err:any) {
      alert("Update failed ");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-100 p-6">
      <div className="max-w-xl mx-auto bg-white shadow rounded-xl p-6">
        <h2 className="text-2xl font-bold mb-4"> Edit Profile</h2>

        <div className="space-y-4">
          <input
            name="fullName"
            value={form.fullName}
            onChange={handleChange}
            placeholder="Full Name"
            className="w-full p-3 border rounded"
          />

          <input
            name="phoneNumber"
            value={form.phoneNumber}
            onChange={handleChange}
            placeholder="Phone Number"
            className="w-full p-3 border rounded"
          />

          <button
            onClick={handleSubmit}
            className="w-full bg-blue-500 text-white p-3 rounded"
          >
            Update Profile
          </button>
        </div>
      </div>
    </div>
  );
}