package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh Token cannot be empty")
    private String refreshToken;

    @NotBlank(message = "Refresh Token cannot be empty")
    private  String username;
}
