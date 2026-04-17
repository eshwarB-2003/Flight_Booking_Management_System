import { useState, ChangeEvent, FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import userApi from "../api/userApi";

interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
  phoneNumber: string;
  role: "ADMIN" | "PASSENGER";
}

export default function AdminRegisterPage() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState<RegisterRequest>({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
    role: "ADMIN",
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>): void => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault();

    try {
      await userApi.post("/register", formData);
      alert("Admin registered successfully");
      navigate("/admin/login");
    } catch (error: any) {
      console.log("FULL ERROR:", error);
      console.log("STATUS:", error.response?.status);
      console.log("DATA:", error.response?.data);
      console.log("PAYLOAD SENT:", formData);

      alert(
        error.response?.data?.message ||
          JSON.stringify(error.response?.data) ||
          error.message ||
          "Admin registration failed"
      );
    }
  };

  return (
    <div className="container auth-container">
      <h1>Admin Register</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input
          name="fullName"
          placeholder="Admin full name"
          value={formData.fullName}
          onChange={handleChange}
          required
        />
        <input
          name="email"
          type="email"
          placeholder="Admin email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Admin password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <input
          name="phoneNumber"
          placeholder="Phone number"
          value={formData.phoneNumber}
          onChange={handleChange}
          required
        />
        <button type="submit">Register</button>
      </form>

      <p>
        Already have an admin account? <Link to="/admin/login">Login</Link>
      </p>
      <p>
        Not an admin? <Link to="/passenger/login">Go to User Login</Link>
      </p>
    </div>
  );
}