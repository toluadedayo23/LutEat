package com.practise.luteat.mapper;

import com.practise.luteat.dto.CreateOrderDto;
import com.practise.luteat.model.Orders;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Mapper(componentModel = "spring")
public abstract class OrdersMapper {

    @Autowired
    private UserRepository userRepository;



    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", expression = "java(findUser(orderDto.getUsername()))")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "orders", source = "menuList")
    public abstract Orders map(CreateOrderDto orderDto);

    User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User with the username: " + username + " not found "
                )
        );
    }
}
