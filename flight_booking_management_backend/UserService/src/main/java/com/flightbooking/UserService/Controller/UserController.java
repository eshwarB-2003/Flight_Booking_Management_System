package com.flightbooking.UserService.Controller;



import com.flightbooking.UserService.DTO.AuthResponse;
import com.flightbooking.UserService.DTO.ChangePasswordRequest;
import com.flightbooking.UserService.DTO.LoginRequest;
import com.flightbooking.UserService.DTO.RegisterRequest;
import com.flightbooking.UserService.DTO.UpdateProfileRequest;
import com.flightbooking.UserService.DTO.UserResponse;
import com.flightbooking.UserService.Service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	@Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userAccountService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userAccountService.login(request));
    }

    @PreAuthorize("hasAnyRole('PASSENGER','ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        return ResponseEntity.ok(userAccountService.getMyProfile(authentication.getName()));
    }

    @PreAuthorize("hasAnyRole('PASSENGER','ADMIN')")
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(Authentication authentication,
                                                      @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userAccountService.updateProfile(authentication.getName(), request));
    }

    @PreAuthorize("hasAnyRole('PASSENGER','ADMIN')")
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(Authentication authentication,
                                                              @Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(Map.of(
                "message", userAccountService.changePassword(authentication.getName(), request)
        ));
    }

    @PreAuthorize("hasAnyRole('PASSENGER','ADMIN')")
    @PutMapping("/deactivate")
    public ResponseEntity<Map<String, String>> deactivate(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", userAccountService.deactivateAccount(authentication.getName())
        ));
    }
}