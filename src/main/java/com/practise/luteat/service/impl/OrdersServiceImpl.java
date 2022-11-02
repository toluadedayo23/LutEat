package com.practise.luteat.service.impl;

import com.practise.luteat.dto.*;
import com.practise.luteat.mapper.MenuOrdersMapper;
import com.practise.luteat.mapper.OrdersMapper;
import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.model.Orders;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.OrderRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.Date;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final UserRepository userRepository;
    private final OrdersMapper ordersMapper;
    private final OrderRepository orderRepository;
    private final MenuOrdersMapper menuOrdersMapper;

    @Transactional
    @Override
    public CreateOrderResponse createOrder(CreateOrderDto orderDto) {
        userRepository.findByUsername(orderDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with the username: " + orderDto.getUsername() + " not found")
                );

        Orders orders = ordersMapper.map(orderDto);
        Double totalPrice = getTotalOrderPrice(orders);
        orders.setTotalPrice(totalPrice);
        orderRepository.save(orders);
        return CreateOrderResponse.builder()
                .username(orders.getUser().getUsername())
                .totalPrice(orders.getTotalPrice())
                .orderDate(Instant.now())
                .order(menuOrderDtoList(orders.getOrders()))
                .build();

    }

    @Override
    public List<OrderResponse> getOrderByDate(OrderBYDateDto orderBYDateDto) {
        User user = userRepository.findByUsername(orderBYDateDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with the username: " + orderBYDateDto.getUsername() + " not found"
                ));
//        Date startDateRepo = Date.valueOf(orderBYDateDto.getFirstDate());
//        Date finalDateRepo = Date.valueOf(orderBYDateDto.getSecondDate());
        List<Tuple> tupleList = orderRepository.getOrdersByDateRange(user.getUserId(), orderBYDateDto.getFirstDate(), orderBYDateDto.getSecondDate());
        List<OrderResponse> orderByDateResponseList = tupleList.stream()
                .map(tuples -> new OrderResponse(
                        tuples.get(0, String.class),
                        tuples.get(1, Double.class),
                        tuples.get(2, java.util.Date.class),
                        tuples.get(3, BigInteger.class)
                        )
                )
                .collect(Collectors.toList());
        return orderByDateResponseList;
    }

    @Override
    public List<OrderByUsernameResponse> getRecentOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with the username: " + username + " not found"
                ));
        List<Tuple> tupleList = orderRepository.getRecentOrdersByUsername(user.getUserId());

        List<OrderByUsernameResponse> orderByUsernameResponse = tupleList.stream()
                .map(tuple -> new OrderByUsernameResponse(
                        tuple.get(0, String.class),
                        tuple.get(1, Double.class),
                        tuple.get(2, java.util.Date.class)
                ))
                .collect(Collectors.toList());

        return orderByUsernameResponse;
    }


    private Double getTotalOrderPrice(Orders orders) {
        Double totalPrice = 0.0;
        Iterator<MenuOrders> order = orders.getOrders().iterator();
        while (order.hasNext()) {
            totalPrice += order.next().getPrice();
        }
        return totalPrice;

    }

    private List<MenuOrderDto> menuOrderDtoList(List<MenuOrders> orders) {
        return orders.stream()
                .map(menuOrdersMapper::mapMenuToDto)
                .collect(Collectors.toList());
    }
}
