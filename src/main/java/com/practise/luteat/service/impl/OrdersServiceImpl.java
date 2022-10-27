package com.practise.luteat.service.impl;

import com.practise.luteat.dto.OrderDto;
import com.practise.luteat.dto.OrdersResponse;
import com.practise.luteat.mapper.OrdersMapper;
import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.model.Orders;
import com.practise.luteat.repository.OrderRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final UserRepository userRepository;
    private final OrdersMapper ordersMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrdersResponse createOrder(OrderDto orderDto) {
        userRepository.findByUsername(orderDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with the username: " + orderDto.getUsername() + " not found")
                );

        Orders orders = ordersMapper.map(orderDto);
        Double totalPrice = getTotalOrderPrice(orders);
        orders.setTotalPrice(totalPrice);
        orderRepository.save(orders);
        return OrdersResponse.builder()
                .username(orders.getUser().getUsername())
                .totalPrice(orders.getTotalPrice())
                .orderDate(Instant.now())
                .order(fillOrderResponseMap(orders.getOrders()))
                .build();

    }

    @Override
    public void getOrderByDate(String username, Date startDate, Date finishDate) {

    }

    @Override
    public void getAllOrdersByUsername(String username) {

    }
    

    private Double getTotalOrderPrice(Orders orders) {
        Double totalPrice = 0.0;
        Iterator<MenuOrders> order = orders.getOrders().iterator();
        while (order.hasNext()) {
            totalPrice += order.next().getPrice();
        }
        return totalPrice;

    }

    private List<String> fillOrderResponseMap(List<MenuOrders> orders) {
        List<String> orderList = new ArrayList<>();
        for (MenuOrders order : orders) {
            orderList.add(order.getName() + ": " + order.getPrice());
        }
        return orderList;
    }
}
