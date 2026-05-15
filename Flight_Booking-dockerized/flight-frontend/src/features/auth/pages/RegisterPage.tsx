import { useState } from "react";
import { register } from "../../api/authApi";
import Input from "../../../shared/ui/Input";
import Button from "../../../shared/ui/Button";

export default function RegisterPage() {
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
  });

  const handleRegister = async () => {
    try {
      await register(form);
      alert("Registered successfully!");
    } catch {
      alert("Registration failed");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-purple-500 to-pink-500">
      <div className="bg-white p-8 rounded-xl shadow-xl w-96">
        <h2 className="text-2xl font-bold mb-6 text-center">Create Account ✈️</h2>

        <Input placeholder="Full Name" onChange={(e) => setForm({ ...form, fullName: e.target.value })} />
        <Input placeholder="Email" className="mt-3" onChange={(e) => setForm({ ...form, email: e.target.value })} />
        <Input type="password" placeholder="Password" className="mt-3" onChange={(e) => setForm({ ...form, password: e.target.value })} />
        <Input placeholder="Phone" className="mt-3" onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })} />

        <div className="mt-6">
          <Button onClick={handleRegister}>Register</Button>
        </div>
      </div>
    </div>
  );
}