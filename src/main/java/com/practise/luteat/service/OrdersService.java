package com.practise.luteat.service;

import com.practise.luteat.dto.CreateOrderDto;
import com.practise.luteat.dto.CreateOrderResponse;
import com.practise.luteat.dto.OrderBYDateDto;
import com.practise.luteat.dto.OrderResponse;

import java.util.List;

public interface OrdersService {
    CreateOrderResponse createOrder(CreateOrderDto orderDto);

    List<OrderResponse> getOrderByDate(OrderBYDateDto orderBYDateDto);

    List<OrderResponse> getRecentOrdersByUsername(String username);
}
