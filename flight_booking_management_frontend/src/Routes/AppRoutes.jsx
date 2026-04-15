import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "../user/pages/LoginPage";
import RegisterPage from "../user/pages/RegisterPage";
import ProfilePage from "../user/pages/ProfilePage";
import ProtectedRoute from "../user/components/ProtectedRoute";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}