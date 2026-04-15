import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import userApi from "../api/userApi";

export default function PassengerProfilePage() {
  const [profile, setProfile] = useState(null);
  const [profileForm, setProfileForm] = useState({ fullName: "", phoneNumber: "" });
  const [passwordForm, setPasswordForm] = useState({ currentPassword: "", newPassword: "" });

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const response = await userApi.get("/profile");
      setProfile(response.data);
      setProfileForm({
        fullName: response.data.fullName || "",
        phoneNumber: response.data.phoneNumber || "",
      });
    } catch (error) {
      alert(error.response?.data?.message || "Failed to load profile");
    }
  };

  const handleProfileChange = (e) => {
    setProfileForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handlePasswordChange = (e) => {
    setPasswordForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleUpdateProfile = async (e) => {
    e.preventDefault();
    try {
      const response = await userApi.put("/profile", profileForm);
      setProfile(response.data);
      alert("Profile updated successfully");
    } catch (error) {
      alert(error.response?.data?.message || "Failed to update profile");
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();
    try {
      await userApi.put("/change-password", passwordForm);
      alert("Password changed successfully");
      setPasswordForm({ currentPassword: "", newPassword: "" });
    } catch (error) {
      alert(error.response?.data?.message || "Failed to change password");
    }
  };

  const handleDeactivate = async () => {
    const confirmed = window.confirm("Are you sure you want to deactivate your account?");
    if (!confirmed) return;

    try {
      await userApi.put("/deactivate");
      localStorage.clear();
      alert("Account deactivated successfully");
      window.location.href = "/passenger/login";
    } catch (error) {
      alert(error.response?.data?.message || "Failed to deactivate account");
    }
  };

  return (
    <>
      <Navbar />
      <div className="container">
        <h1>User Profile</h1>

        {profile && (
          <div className="card-box">
            <p><strong>User ID:</strong> {profile.userId}</p>
            <p><strong>Email:</strong> {profile.email}</p>
            <p><strong>Role:</strong> {profile.role}</p>
            <p><strong>Status:</strong> {profile.accountStatus}</p>
          </div>
        )}

        <div className="card-box">
          <h2>Update Profile</h2>
          <form className="form" onSubmit={handleUpdateProfile}>
            <input name="fullName" placeholder="Full Name" value={profileForm.fullName} onChange={handleProfileChange} required />
            <input name="phoneNumber" placeholder="Phone Number" value={profileForm.phoneNumber} onChange={handleProfileChange} required />
            <button type="submit">Update Profile</button>
          </form>
        </div>

        <div className="card-box">
          <h2>Change Password</h2>
          <form className="form" onSubmit={handleChangePassword}>
            <input name="currentPassword" type="password" placeholder="Current Password" value={passwordForm.currentPassword} onChange={handlePasswordChange} required />
            <input name="newPassword" type="password" placeholder="New Password" value={passwordForm.newPassword} onChange={handlePasswordChange} required />
            <button type="submit">Change Password</button>
          </form>
        </div>

        <div className="card-box">
          <h2>Deactivate Account</h2>
          <button className="danger-btn" onClick={handleDeactivate}>Deactivate My Account</button>
        </div>
      </div>
    </>
  );
}

