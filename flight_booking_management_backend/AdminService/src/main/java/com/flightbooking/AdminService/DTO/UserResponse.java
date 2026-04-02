package com.flightbooking.AdminService.DTO;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;
    private String accountStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}