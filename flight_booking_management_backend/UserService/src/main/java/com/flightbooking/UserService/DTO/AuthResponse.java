package com.flightbooking.UserService.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String tokenType;
    private Integer userId;
    private String email;
    private String role;
    private String accountStatus;
    private String message;
}