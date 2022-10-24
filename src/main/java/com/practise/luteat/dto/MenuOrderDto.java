package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class MenuOrderDto {
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @Size(min = 2, max = 10, message = "name must be between 2 and 10 characters")
    private String name;

    @NotBlank(message = "price cannot be null")
    @Size(min = 3, message = "price must be at least 3 figures")
    private Double price;
}
