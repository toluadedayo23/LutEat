package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class OrdersByUsernameDto {

    @NotBlank(message = "firstname must not be empty")
    @Size(min = 2, max = 15, message = "firstname must be between 2 and 15 characters")
    private String username;
}
