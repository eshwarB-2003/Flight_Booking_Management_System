import { useState } from "react";
import Navbar from "../components/Navbar";
import adminApi from "../api/adminApi";

export default function AdminDashboard() {
  const [users, setUsers] = useState([]);
  const [auditLogs, setAuditLogs] = useState([]);
  const [report, setReport] = useState(null);
  const [health, setHealth] = useState("");
  const [userIdInput, setUserIdInput] = useState("");

  const clearOtherSections = () => {
    setUsers([]);
    setReport(null);
    setHealth("");
  };

  const loadAllUsers = async () => {
    try {
      setAuditLogs([]);
      setReport(null);
      setHealth("");

      const response = await adminApi.get("/users");
      setUsers(Array.isArray(response.data) ? response.data : []);
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to load users");
    }
  };

  const loadAuditLogs = async () => {
    try {
      setUsers([]);
      setReport(null);
      setHealth("");

      const response = await adminApi.get("/audit-logs");
      const logs = Array.isArray(response.data) ? response.data : [];
      setAuditLogs(logs);

      if (logs.length === 0) {
        alert("No audit logs found");
      }
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to load audit logs");
    }
  };

  const loadAuditLogsByUser = async () => {
    if (!userIdInput) {
      alert("Please enter a user ID");
      return;
    }

    try {
      clearOtherSections();

      const response = await adminApi.get(`/audit-logs/user/${userIdInput}`);
      const logs = Array.isArray(response.data) ? response.data : [];
      setAuditLogs(logs);

      if (logs.length === 0) {
        alert("No audit logs found for this user ID");
      } else {
        setTimeout(() => {
          const section = document.getElementById("audit-log-section");
          if (section) {
            section.scrollIntoView({ behavior: "smooth", block: "start" });
          }
        }, 100);
      }
    } catch (error) {
      console.error("Audit log fetch error:", error);
      alert(
        error.response?.data?.message ||
          "Failed to load user audit logs. Make sure you entered a User ID, not a Log ID."
      );
    }
  };

  const loadReport = async () => {
    try {
      setUsers([]);
      setAuditLogs([]);
      setHealth("");

      const response = await adminApi.get("/reports");
      setReport(response.data);
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to load report");
    }
  };

  const loadHealth = async () => {
    try {
      setUsers([]);
      setAuditLogs([]);
      setReport(null);

      const response = await adminApi.get("/system-health");
      setHealth(response.data.message);
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to load system health");
    }
  };

  const activateUser = async () => {
    if (!userIdInput) {
      alert("Please enter a user ID");
      return;
    }

    try {
      const response = await adminApi.put(`/users/${userIdInput}/activate`);
      alert(response.data.message || "User activated successfully");
      loadAllUsers();
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to activate user");
    }
  };

  const deactivateUser = async () => {
    if (!userIdInput) {
      alert("Please enter a user ID");
      return;
    }

    try {
      const response = await adminApi.put(`/users/${userIdInput}/deactivate`);
      alert(response.data.message || "User deactivated successfully");
      loadAllUsers();
    } catch (error) {
      console.error(error);
      alert(error.response?.data?.message || "Failed to deactivate user");
    }
  };

  return (
    <>
      <Navbar />
      <div className="container wide-container">
        <h1>Admin Dashboard</h1>

        <div className="admin-actions">
          <button onClick={loadAllUsers}>View All Users</button>
          <button onClick={loadAuditLogs}>View All Audit Logs</button>
          <button onClick={loadReport}>Generate System Report</button>
          <button onClick={loadHealth}>Check System Health</button>
        </div>

        <div className="admin-actions">
          <input
            type="number"
            placeholder="Enter User ID (not Log ID)"
            value={userIdInput}
            onChange={(e) => setUserIdInput(e.target.value)}
          />
          <button onClick={loadAuditLogsByUser}>View User Audit Logs</button>
          <button onClick={activateUser}>Activate User</button>
          <button onClick={deactivateUser}>Deactivate User</button>
        </div>

        <div className="card-box" style={{ marginTop: "16px" }}>
          <strong>Entered User ID:</strong> {userIdInput || "None"}
          <br />
          <strong>Audit Log Count:</strong> {auditLogs.length}
        </div>

        {health && (
          <div className="card-box">
            <strong>System Health:</strong> {health}
          </div>
        )}

        {report && (
          <div className="card-box">
            <h2>System Report</h2>
            <p><strong>Total Bookings:</strong> {report.totalBookings}</p>
            <p><strong>Total Revenue:</strong> {report.totalRevenue}</p>
            <p><strong>Active Flights:</strong> {report.activeFlights}</p>
            <p><strong>Generated At:</strong> {report.generatedAt}</p>
          </div>
        )}

        {users.length > 0 && (
          <div className="table-box">
            <h2>Users</h2>
            <table>
              <thead>
                <tr>
                  <th>User ID</th>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Role</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.userId}>
                    <td>{user.userId}</td>
                    <td>{user.fullName}</td>
                    <td>{user.email}</td>
                    <td>{user.phoneNumber}</td>
                    <td>{user.role}</td>
                    <td>{user.accountStatus}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {auditLogs.length > 0 && (
          <div className="table-box" id="audit-log-section">
            <h2>Audit Logs</h2>
            <table>
              <thead>
                <tr>
                  <th>Log ID</th>
                  <th>User ID</th>
                  <th>Action</th>
                  <th>Entity Type</th>
                  <th>Entity ID</th>
                  <th>Timestamp</th>
                  <th>Details</th>
                </tr>
              </thead>
              <tbody>
                {auditLogs.map((log) => (
                  <tr key={log.logId}>
                    <td>{log.logId}</td>
                    <td>{log.userId}</td>
                    <td>{log.action}</td>
                    <td>{log.entityType}</td>
                    <td>{log.entityId ?? "N/A"}</td>
                    <td>{log.timestamp ?? "N/A"}</td>
                    <td>{log.details}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </>
  );
}