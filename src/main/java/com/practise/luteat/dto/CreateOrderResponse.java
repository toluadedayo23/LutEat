package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    private String username;
    private Double totalPrice;
    private Date orderDate;
    private List<MenuOrderDto> order;
}
