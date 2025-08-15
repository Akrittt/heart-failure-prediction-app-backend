package com.health.care.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String firstname;

    public JwtAuthenticationResponse(String accessToken, String firstname) {
        this.accessToken = accessToken;
        this.firstname = firstname;
    }
}
