import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import PassengerLoginPage from "../user/pages/PassengerLoginPage";
import PassengerRegisterPage from "../user/pages/PassengerRegisterPage";
import PassengerProfilePage from "../user/pages/PassengerProfilePage";
import PassengerDashboard from "../user/pages/PassengerDashboard";
import AdminLoginPage from "../user/pages/AdminLoginPage";
import AdminRegisterPage from "../user/pages/AdminRegisterPage";
import AdminDashboard from "../user/pages/AdminDashboard";
import ProtectedRoute from "../user/components/ProtectedRoute";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/passenger/login" replace />} />

        <Route path="/passenger/login" element={<PassengerLoginPage />} />
        <Route path="/passenger/register" element={<PassengerRegisterPage />} />

        <Route path="/admin/login" element={<AdminLoginPage />} />
        <Route path="/admin/register" element={<AdminRegisterPage />} />

        <Route
          path="/passenger/dashboard"
          element={
            <ProtectedRoute allowedRole="PASSENGER">
              <PassengerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/passenger/profile"
          element={
            <ProtectedRoute allowedRole="PASSENGER">
              <PassengerProfilePage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}