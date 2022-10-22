package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotEmpty(message = "Refresh Token cannot be empty")
    private String refreshToken;

    @NotEmpty(message = "Refresh Token cannot be empty")
    private  String username;
}
