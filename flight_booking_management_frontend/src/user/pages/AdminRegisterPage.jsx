import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userApi from "../api/userApi";

export default function AdminRegisterPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
    role: "ADMIN",
  });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await userApi.post("/register", formData);
      alert("Admin registered successfully");
      navigate("/admin/login");
    } catch (error) {
      alert(error.response?.data?.message || "Admin registration failed");
    }
  };

  return (
    <div className="container auth-container">
      <h1>Admin Register</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input name="fullName" placeholder="Admin full name" value={formData.fullName} onChange={handleChange} required />
        <input name="email" type="email" placeholder="Admin email" value={formData.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Admin password" value={formData.password} onChange={handleChange} required />
        <input name="phoneNumber" placeholder="Phone number" value={formData.phoneNumber} onChange={handleChange} required />
        <button type="submit">Register</button>
      </form>

      <p>Already have an admin account? <Link to="/admin/login">Login</Link></p>
      <p>Not an admin? <Link to="/passenger/login">Go to User Login</Link></p>
    </div>
  );
}

