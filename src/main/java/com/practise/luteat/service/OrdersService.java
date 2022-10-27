package com.practise.luteat.service;

import com.practise.luteat.dto.OrderDto;
import com.practise.luteat.dto.OrdersResponse;

import java.util.Date;

public interface OrdersService {
    OrdersResponse createOrder(OrderDto orderDto);

    void getOrderByDate(String username, Date startDate, Date finishDate);

    void getAllOrdersByUsername(String username);
}
