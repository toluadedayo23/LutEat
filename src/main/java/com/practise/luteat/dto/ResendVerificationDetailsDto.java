package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ResendVerificationDetailsDto {

    @NotEmpty(message = "username cannot be empty")
    private String username;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    private String email;
}
