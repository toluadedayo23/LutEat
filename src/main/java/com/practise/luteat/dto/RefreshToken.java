package com.practise.luteat.dto;

import javax.validation.constraints.NotEmpty;

public class RefreshToken {

    @NotEmpty(message = "refreshToken cannot be empty")
    private String refreshToken;

    @NotEmpty(message = "username cannot be empty")
    private String username;

}
