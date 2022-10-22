package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty
    @Size(min = 2, max = 10)
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;
}
