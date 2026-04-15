import { Routes, Route } from "react-router-dom";
import { ROUTES } from "./routeConstants";

import Home from "../pages/Home";
import Login from "../pages/auth/Login";
import SearchFlights from "../pages/flight/SearchFlights";
import FlightResults from "../pages/flight/FlightResults";
import SeatSelection from "../pages/flight/SeatSelection";
import BookingSummary from "../pages/booking/BookingSummary";
import AddAncillary from "../pages/ancillary/AddAncillary";
import Dashboard from "../pages/admin/Dashboard";
import Users from "../pages/admin/Users";
import Reports from "../pages/admin/Reports";
import AuditLogs from "../pages/admin/AuditLogs";
import NotFound from "../pages/NotFound";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path={ROUTES.HOME} element={<Home />} />
      <Route path={ROUTES.LOGIN} element={<Login />} />

      {/* FLOW */}
      <Route path={ROUTES.SEARCH_FLIGHTS} element={<SearchFlights />} />
      <Route path={ROUTES.FLIGHT_RESULTS} element={<FlightResults />} />
      <Route path={ROUTES.SEAT_SELECTION} element={<SeatSelection />} />

      <Route path={ROUTES.BOOKING_SUMMARY} element={<BookingSummary />} />
      <Route path={ROUTES.ANCILLARY} element={<AddAncillary />} />

      {/* ADMIN */}
      <Route path={ROUTES.ADMIN_DASHBOARD} element={<Dashboard />} />
      <Route path={ROUTES.ADMIN_USERS} element={<Users />} />
      <Route path={ROUTES.ADMIN_REPORTS} element={<Reports />} />
      <Route path={ROUTES.ADMIN_AUDIT_LOGS} element={<AuditLogs />} />

      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

export default AppRoutes;