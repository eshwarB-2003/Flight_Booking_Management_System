import { create } from "zustand";

interface AuthState {
  token: string | null;
  user: any;
  setAuth: (data: any) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  token: localStorage.getItem("token"),
  user: null,

  setAuth: (data) => {
    localStorage.setItem("token", data.token);
    set({ token: data.token, user: data });
  },

  logout: () => {
    localStorage.removeItem("token");
    set({ token: null, user: null });
  },
}));