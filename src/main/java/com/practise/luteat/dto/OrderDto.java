package com.practise.luteat.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class OrderDto {

    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 2, max = 10)
    private String username;

    @NotNull(message = "price cannot be null")
    @Size(min=3)
    private Double totalPrice;

    @NotEmpty(message = "Menu List cannot be empty")
    @Size(min=1, message = "Must contain at least one Menu")
    private List<MenuOrderDto> menuList;
}
