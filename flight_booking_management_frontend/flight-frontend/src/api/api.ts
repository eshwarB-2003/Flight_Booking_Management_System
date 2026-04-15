/* const BASE_URL = "http://localhost:8080/api";

export const api = {
  searchFlights: async (from: string, to: string, date: string) => {
    const res = await fetch(
      `${BASE_URL}/schedules/search?from=${from}&to=${to}&date=${date}`
    );
    return res.json();
  },

  // 
  getSeats: async (scheduleId: number) => {
    const res = await fetch(`${BASE_URL}/seats/${scheduleId}`);
    return res.json();
  },

  lockSeat: async (scheduleId: number, seatNumber: string) => {
    const res = await fetch(
      `${BASE_URL}/seats/${scheduleId}/lock?seatNumber=${seatNumber}`,
      { method: "POST" }
    );
    return res.json();
  },

  bookSeat: async (scheduleId: number, seatNumber: string) => {
    const res = await fetch(
      `${BASE_URL}/seats/${scheduleId}/book?seatNumber=${seatNumber}`,
      { method: "POST" }
    );
    return res.json();
  },
};
*/

const BASE_URL = "http://localhost:8080/api";

// ✅ ADD THIS (IMPORTANT)
const getHeaders = () => ({
  "Content-Type": "application/json",
  Authorization: `Bearer ${localStorage.getItem("token") || ""}`,
});

export const api = {
  // 🔍 SEARCH FLIGHTS
  searchFlights: async (from: string, to: string, date: string) => {
    const res = await fetch(
      `${BASE_URL}/schedules/search?from=${from}&to=${to}&date=${date}`,
      { headers: getHeaders() } // ✅ added
    );
    return res.json();
  },

  // 🪑 GET SEATS
  getSeats: async (scheduleId: number) => {
    const res = await fetch(`${BASE_URL}/seats/${scheduleId}`, {
      headers: getHeaders(), // ✅ added
    });
    return res.json();
  },

  // 🔒 LOCK SEAT
  lockSeat: async (scheduleId: number, seatNumber: string) => {
    const res = await fetch(
      `${BASE_URL}/seats/${scheduleId}/lock?seatNumber=${seatNumber}`,
      {
        method: "POST",
        headers: getHeaders(), // ✅ added
      }
    );
    return res.json();
  },

  // ✅ BOOK SEAT
  bookSeat: async (scheduleId: number, seatNumber: string) => {
    const res = await fetch(
      `${BASE_URL}/seats/${scheduleId}/book?seatNumber=${seatNumber}`,
      {
        method: "POST",
        headers: getHeaders(), // ✅ added
      }
    );
    return res.json();
  },
};
