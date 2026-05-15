package com.flightbooking.UserService.Service;





import com.flightbooking.UserService.DTO.*;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getMyProfile(String email);

    UserResponse updateProfile(String email, UpdateProfileRequest request);

    String changePassword(String email, ChangePasswordRequest request);

    String deactivateAccount(String email);
}