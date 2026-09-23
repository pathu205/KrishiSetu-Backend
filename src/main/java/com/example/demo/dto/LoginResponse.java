package com.example.demo.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String message;
    private UserInfo user;

    public LoginResponse(String token, String message, UserInfo user) {
        this.token = token;
        this.message = message;
        this.user = user;
    }

    @Data
    public static class UserInfo {

        private String id;
        private String name;
        private String email;
        private String phone;
        private String role;

        public UserInfo(String id, String name, String email, String phone, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.role = role;
        }
    }
}