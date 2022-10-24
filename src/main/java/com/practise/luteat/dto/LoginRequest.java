package com.practise.luteat.dto;

import com.practise.luteat.customValidator.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username cannot be empty")
    @Size(min = 2, max = 10, message = "username must be between 2 and 10 characters")
    private String username;

    @NotBlank(message = "password cannot be empty")
    @Password
    private String password;
}
