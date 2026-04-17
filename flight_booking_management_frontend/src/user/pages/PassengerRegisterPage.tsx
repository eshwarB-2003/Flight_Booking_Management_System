import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import userApi from "../api/userApi";

export default function PassengerRegisterPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
    role: "PASSENGER",
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
      alert("User registered successfully");
      navigate("/passenger/login");
    } catch (error) {
      alert(error.response?.data?.message || "User registration failed");
    }
  };

  return (
    <div className="container auth-container">
      <h1>User Register</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input name="fullName" placeholder="Full Name" value={formData.fullName} onChange={handleChange} required />
        <input name="email" type="email" placeholder="Email" value={formData.email} onChange={handleChange} required />
        <input name="password" type="password" placeholder="Password" value={formData.password} onChange={handleChange} required />
        <input name="phoneNumber" placeholder="Phone Number" value={formData.phoneNumber} onChange={handleChange} required />
        <button type="submit">Register</button>
      </form>

      <p>Already have a user account? <Link to="/passenger/login">Login</Link></p>
      <p>Not a user? <Link to="/admin/login">Go to Admin Login</Link></p>
    </div>
  );
}

