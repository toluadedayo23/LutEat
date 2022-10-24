package com.practise.luteat.dto;

import com.practise.luteat.customValidator.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class signupRequest {

    @NotBlank(message = "firstname must not be empty")
    @Size(min = 2, max = 15, message = "firstname must be between 2 and 15 characters")
    private String firstname;

    @NotBlank(message = "lastname must not be empty")
    @Size(min = 2, max = 15,message = "lastname must be between 2 and 15 characters" )
    private String lastname;

    @NotBlank(message = "username must not be empty")
    @Size(min = 2, max = 15, message = "username must be between 2 and 15 characters")
    private String username;

    @NotBlank(message = "Email is compulsory, please provide a valid one")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
    message = "please provide a valid email")
    private String email;

    @NotBlank(message = "phonenumber must not be empty")
    @Size(min = 11, max = 13, message = "email must be between 2 and 10 characters")
    private String phonenumber;

    @NotBlank(message = "password cannot be empty")
    @Password
    private String password;


}
