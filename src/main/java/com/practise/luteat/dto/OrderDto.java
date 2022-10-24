package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
public class OrderDto {

    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 2, max = 10, message = "username must be between 2 and 10 characters")
    private String username;

    @NotBlank(message = "price cannot be null")
    @Size(min=3, message = "price must be at least 3 figures")
    private Double totalPrice;

    @NotEmpty(message = "Menu List cannot be empty")
    @Size(min=1, message = "Must contain at least one Menu")
    private List<MenuOrderDto> menuList;
}
