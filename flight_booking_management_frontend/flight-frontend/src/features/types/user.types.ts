export interface UserResponse {
  userId: number;
  fullName: string;
  email: string;
  phoneNumber: string;
  role: string;
  accountStatus: string;
  createdAt: string;
  updatedAt: string;
}

export interface UpdateProfileRequest {
  fullName: string;
  phoneNumber: string;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}