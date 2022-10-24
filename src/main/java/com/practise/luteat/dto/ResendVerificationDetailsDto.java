package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ResendVerificationDetailsDto {

    @NotBlank(message = "username cannot be empty")
    private String username;


    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email")
    private String email;
}
