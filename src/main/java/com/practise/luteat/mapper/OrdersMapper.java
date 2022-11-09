package com.practise.luteat.mapper;

import com.practise.luteat.dto.CreateOrderDto;
import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.exceptions.OrderException;
import com.practise.luteat.model.Orders;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class OrdersMapper {

    @Autowired
    private UserRepository userRepository;


    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", expression = "java(findUser(orderDto.getUsername()))")
    @Mapping(target = "createdDate", expression = "java(returnDate())")
    @Mapping(target = "orders", source = "menuList")
    @Mapping(target = "totalPrice", expression = "java(getTotalOrderPrice(orderDto))")
    public abstract Orders map(CreateOrderDto orderDto);

    User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User with the username: " + username + " not found "
                )
        );
    }

    Double getTotalOrderPrice(CreateOrderDto orderDto) {
        if (orderDto != null) {
            Double totalPrice = 0.0;
            for (MenuOrderDto order : orderDto.getMenuList()) {
                if (order.getPrice() == null) {
                    throw new OrderException("order was empty, please re-order again");
                }
                totalPrice += order.getPrice();
            }
            return totalPrice;
        }
        throw new OrderException("Order is null, please re-order again");
    }

    Date returnDate(){
        return new Date();
    }
}
