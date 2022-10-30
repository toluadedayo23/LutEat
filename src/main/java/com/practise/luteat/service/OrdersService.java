package com.practise.luteat.service;

import com.practise.luteat.dto.*;

import java.util.List;

public interface OrdersService {
    CreateOrderResponse createOrder(createOrderDto orderDto);

    List<OrderResponse> getOrderByDate(OrderBYDateDto orderBYDateDto);

    List<OrderResponse> getRecentOrdersByUsername(OrdersByUsernameDto ordersByUsernameDto);
}
