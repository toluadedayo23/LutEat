package com.practise.luteat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class OrdersResponse {
    private String username;
    private Double totalPrice;
    private Instant orderDate;
    private List<String> order;
}
