import { Routes, Route } from "react-router-dom";
import LoginPage from "../features/auth/pages/LoginPage";
import RegisterPage from "../features/auth/pages/RegisterPage";
import DashboardPage from "../features/user/pages/DashboardPage";
import ProfilePage from "../features/user/pages/ProfilePage";
import ProtectedRoute from "./ProtectedRoute";
import EditProfilePage from "../features/user/pages/EditProfilePage";
import ChangePasswordPage from "../features/user/pages/ChangePasswordPage";
import SearchResultsPage from "../features/user/pages/SearchResultsPage";



export default function AppRoutes() {
  return (
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />
          <Route
  path="/edit-profile"
  element={
    <ProtectedRoute>
      <EditProfilePage />
    </ProtectedRoute>
  }
/>

<Route
  path="/change-password"
  element={
    <ProtectedRoute>
      <ChangePasswordPage />
    </ProtectedRoute>
  }
/>
  <Route
  path="/search-results"
  element={
    <ProtectedRoute>
      <SearchResultsPage />
    </ProtectedRoute>
  }
/>

<Route
  path="/search-results"
  element={
    <ProtectedRoute>
      <SearchResultsPage />
    </ProtectedRoute>
  }
/>
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <ProfilePage />
            </ProtectedRoute>
          }
        />
      </Routes>
      

      
  );
}