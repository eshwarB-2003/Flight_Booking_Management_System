export const API_ENDPOINTS = {
  // USER
  USER: {
    REGISTER: "/api/users/register",
    LOGIN: "/api/users/login",
    PROFILE: "/api/users/profile",
    CHANGE_PASSWORD: "/api/users/change-password",
    DEACTIVATE: "/api/users/deactivate",
    MY_BOOKINGS: "/api/users/bookings",
  },

  // BOOKING
  BOOKING: {
    CREATE: "/api/bookings",
    CONFIRM: (id: number) => `/api/bookings/${id}/confirm`,
    CANCEL: (id: number) => `/api/bookings/${id}/cancel`,
    GET_BY_ID: (id: number) => `/api/bookings/${id}`,
    MY_BOOKINGS: "/api/bookings/my-bookings",
  },

  // ANCILLARY
  ANCILLARY: {
    ADD: (bookingId: number) =>
      `/api/bookings/${bookingId}/ancillaries`,
    REMOVE: (bookingId: number, ancillaryId: number) =>
      `/api/bookings/${bookingId}/ancillaries/${ancillaryId}`,
    GET: (bookingId: number) =>
      `/api/bookings/${bookingId}/ancillaries`,
  },

  // ADMIN
  ADMIN: {
    USERS: "/api/admin/users",
    USER_BY_ID: (id: number) => `/api/admin/users/${id}`,
    ACTIVATE: (id: number) => `/api/admin/users/${id}/activate`,
    DEACTIVATE: (id: number) => `/api/admin/users/${id}/deactivate`,
    REPORTS: "/api/admin/reports",
    AUDIT_LOGS: "/api/admin/audit-logs",
  },

  // FLIGHT
  FLIGHT: {
    SEARCH: "/api/flights/search",
    DETAILS: (id: number) => `/api/flights/${id}`,
    SEAT_MAP: (id: number) => `/api/flights/${id}/seats`,
  },
};