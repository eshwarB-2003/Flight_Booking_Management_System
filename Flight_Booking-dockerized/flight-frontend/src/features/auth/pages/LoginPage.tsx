import { useState } from "react";
import { login } from "../../api/authApi";
import { useAuthStore } from "../../../store/authStore";
import { useNavigate } from "react-router-dom";
import Input from "../../../shared/ui/Input";
import Button from "../../../shared/ui/Button";

export default function LoginPage() {
    const navigate = useNavigate();
  const setAuth = useAuthStore((s) => s.setAuth);

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

   const handleLogin = async () => {
     console.log("Login clicked");
    try {
      const res = await login(form);
      console.log("Response:", res); // 

      setAuth(res);

      navigate("/dashboard"); 
    } catch (err) {
      alert("Login failed");
    }
  };


  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-blue-500 to-indigo-600">
      <div className="bg-white p-8 rounded-xl shadow-xl w-96">
        <h2 className="text-2xl font-bold mb-6 text-center">Welcome Back ✈️</h2>

        <Input
          placeholder="Email"
          onChange={(e) => setForm({ ...form, email: e.target.value })}
        />
        <Input
          type="password"
          placeholder="Password"
          className="mt-4"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <div className="mt-6">
          <Button onClick={handleLogin}>Login</Button>
        </div>
      </div>
    </div>
  );
}