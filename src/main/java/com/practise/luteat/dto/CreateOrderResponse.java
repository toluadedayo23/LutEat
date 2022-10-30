package com.practise.luteat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CreateOrderResponse {
    private String username;
    private Double totalPrice;
    private Instant orderDate;
    private List<MenuOrderDto> order;
}
