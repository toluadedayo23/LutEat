package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class OrderBYDateDto {

    @NotBlank(message = "username must not be empty")
    @Size(min = 2, max = 15, message = "username must be between 2 and 15 characters")
    private String username;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "date field cannot be blank")
    private String firstDate;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "date field cannot be blank")
    private String secondDate;
}
