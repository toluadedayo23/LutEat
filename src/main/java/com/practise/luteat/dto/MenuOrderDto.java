package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class MenuOrderDto {
    private Long id;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 2, max = 10)
    private String name;

    @NotNull(message = "price cannot be null")
    @Size(min = 3)
    private Double price;
}
