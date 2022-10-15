package com.practise.luteat.dto;

import com.practise.luteat.customValidator.Password;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
public class RegisterRequest {

    @NotEmpty
    @Size(min = 2, max = 10)
    private String firstname;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String lastname;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String username;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 11, max = 13)
    private String phonenumber;

    @NotEmpty(message = "password cannot be empty")
    @Password
    private String password;


}
