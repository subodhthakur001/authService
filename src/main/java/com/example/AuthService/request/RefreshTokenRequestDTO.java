package com.example.AuthService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RefreshTokenRequestDTO {
    private String token;

    public RefreshTokenRequestDTO(String token) {
        this.token = token;
    }
    public RefreshTokenRequestDTO(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
