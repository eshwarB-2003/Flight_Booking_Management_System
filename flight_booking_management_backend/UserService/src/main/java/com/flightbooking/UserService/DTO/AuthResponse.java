package com.flightbooking.UserService.DTO;

public class AuthResponse {

    private String token;
    private String tokenType;
    private Integer userId;
    private String email;
    private String role;
    private String accountStatus;
    private String message;

    private AuthResponse(AuthResponseBuilder builder) {
        this.token = builder.token;
        this.tokenType = builder.tokenType;
        this.userId = builder.userId;
        this.email = builder.email;
        this.role = builder.role;
        this.accountStatus = builder.accountStatus;
        this.message = builder.message;
    }

    public AuthResponse() {}

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType;
        private Integer userId;
        private String email;
        private String role;
        private String accountStatus;
        private String message;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public AuthResponseBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public AuthResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public AuthResponseBuilder accountStatus(String accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public AuthResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}