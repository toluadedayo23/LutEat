package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
public class createOrderDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, max = 10, message = "username must be between 2 and 10 characters")
    private String username;


    @NotEmpty(message = "Menu List cannot be empty")
    @Size(min=1, message = "Must contain at least one Menu")
    private List<MenuOrderDto> menuList;
}
