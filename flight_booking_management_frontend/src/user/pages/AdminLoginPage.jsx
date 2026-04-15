import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userApi from "../api/userApi";

export default function AdminLoginPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ email: "", password: "" });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await userApi.post("/login", formData);

      if (response.data.role !== "ADMIN") {
        alert("This page is for admin login only.");
        return;
      }

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role);
      localStorage.setItem("email", response.data.email);
      localStorage.setItem("userId", response.data.userId);

      alert("Admin login successful");
      navigate("/admin/dashboard");
    } catch (error) {
      alert(error.response?.data?.message || "Invalid email or incorrect password");
    }
  };

  return (
    <div className="container auth-container">
      <h1>Admin Login</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input name="email" type="email" placeholder="Admin email" value={formData.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Admin password" value={formData.password} onChange={handleChange} required />
        <button type="submit">Login</button>
      </form>

      <p>Do not have an admin account? <Link to="/admin/register">Register</Link></p>
      <p>Not an admin? <Link to="/passenger/login">Go to User Login</Link></p>
    </div>
  );
}

