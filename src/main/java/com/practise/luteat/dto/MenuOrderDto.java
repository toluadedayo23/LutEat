package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class MenuOrderDto {

    @NotBlank(message = "username must not be empty")
    @Size(min = 2, max = 15, message = "name must be between 2 and 15 characters")
    private String name;


    @Range(max = 10000, message = "price cannot be greater than 10000")
    private Double price;
}
