package com.health.care.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    private String email;
    private String otp;
    private String firstname;
    private String lastname;
    private String hospitalName;
    private String password;
}
