package com.flightbooking.UserService.Service;

import com.flightbooking.UserService.DTO.AuthResponse;
import com.flightbooking.UserService.DTO.ChangePasswordRequest;
import com.flightbooking.UserService.DTO.LoginRequest;
import com.flightbooking.UserService.DTO.RegisterRequest;
import com.flightbooking.UserService.DTO.UpdateProfileRequest;
import com.flightbooking.UserService.DTO.UserResponse;
import com.flightbooking.UserService.Entity.User;
import com.flightbooking.UserService.Repository.UserRepository;
import com.flightbooking.UserService.Security.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAccountService {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String assignedRole = (request.getRole() != null && !request.getRole().isBlank())
                ? request.getRole().toUpperCase()
                : "PASSENGER";

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(assignedRole)
                .accountStatus("ACTIVE")
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                savedUser.getEmail(),
                savedUser.getUserId(),
                savedUser.getRole()
        );

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .accountStatus(savedUser.getAccountStatus())
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ACTIVE".equalsIgnoreCase(user.getAccountStatus())) {
            throw new RuntimeException("Account is deactivated");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getUserId(),
                user.getRole()
        );

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getUserId())
                .email(user.getEmail())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .message("Login successful")
                .build();
    }

    public UserResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserResponse(user);
    }

    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    public String changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return "Password changed successfully";
    }

    public String deactivateAccount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus("DEACTIVATED");
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return "Account deactivated successfully";
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}