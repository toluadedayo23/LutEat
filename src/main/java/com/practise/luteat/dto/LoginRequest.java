package com.practise.luteat.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotEmpty
    @Size(min = 2, max = 10)
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;
}
