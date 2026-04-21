import api from "../../shared/services/api";
import type { LoginRequest, RegisterRequest, AuthResponse } from "../types/auth.types";

export const login = async (data: LoginRequest): Promise<AuthResponse> => {
  const res = await api.post("/api/users/login", data);
  return res.data;
};

export const register = async (data: RegisterRequest): Promise<AuthResponse> => {
  const res = await api.post("/api/users/register", data);
  return res.data;
};
export const getProfile = async () => {
  const res = await api.get("/api/users/profile");
  return res.data;
};