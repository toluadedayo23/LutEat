package com.practise.luteat.controller;

import com.practise.luteat.dto.*;
import com.practise.luteat.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/get-recent-orders-by/{username}")
    public ResponseEntity<List<OrderByUsernameResponse>> getRecentOrdersByUsername(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getRecentOrdersByUsername(username));
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.createOrder(createOrderDto));
    }


    @PostMapping("/get-order-by-date")
    public ResponseEntity<List<OrderResponse>> getOrderByDateRange(@Valid @RequestBody OrderBYDateDto orderBYDateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrderByDate(orderBYDateDto));
    }

}
