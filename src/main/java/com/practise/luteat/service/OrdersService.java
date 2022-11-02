package com.practise.luteat.service;

import com.practise.luteat.dto.*;

import java.util.List;

public interface OrdersService {
    CreateOrderResponse createOrder(CreateOrderDto orderDto);

    List<OrderResponse> getOrderByDate(OrderBYDateDto orderBYDateDto);

    List<OrderByUsernameResponse> getRecentOrdersByUsername(String username);
}
