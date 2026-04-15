import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userApi from "../api/userApi";

export default function PassengerLoginPage() {
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

      if (response.data.role !== "PASSENGER") {
        alert("This page is for passenger login only.");
        return;
      }

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role);
      localStorage.setItem("email", response.data.email);
      localStorage.setItem("userId", response.data.userId);

      alert("User login successful");
      navigate("/passenger/dashboard");
    } catch (error) {
      alert(error.response?.data?.message || "Invalid email or incorrect password");
    }
  };

  return (
    <div className="container auth-container">
      <h1>User Login</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input
          name="email"
          type="email"
          placeholder="Enter your email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Enter your password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>

      <p>Do not have a user account? <Link to="/passenger/register">Register</Link></p>
      <p>Not a user? <Link to="/admin/login">Go to Admin Login</Link></p>
    </div>
  );
}


