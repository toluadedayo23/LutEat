package com.practise.luteat.serviceTest;


import com.practise.luteat.dto.CreateOrderDto;
import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.dto.OrderBYDateDto;
import com.practise.luteat.mapper.MenuOrdersMapper;
import com.practise.luteat.mapper.OrdersMapper;
import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.model.Orders;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.OrderRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.AuthService;
import com.practise.luteat.service.impl.OrdersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Tuple;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrdersMapper ordersMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuOrdersMapper menuOrdersMapper;


    @Mock
    private OrdersServiceImpl ordersServiceImpl;

    @Captor
    ArgumentCaptor<Orders> ordersArgumentCaptor;

    @BeforeEach
    public void setUp() {
        ordersServiceImpl = new OrdersServiceImpl(userRepository, ordersMapper, orderRepository, menuOrdersMapper);
    }

    @Test
    @DisplayName("should create order")
    public void shouldCreateOrder() {

        MenuOrderDto menuOrderDto = new MenuOrderDto("menu1", 1400.00);
        MenuOrderDto menuOrderDto2 = new MenuOrderDto("menu2", 1500.00);
        List<MenuOrderDto> menuList = Arrays.asList(menuOrderDto, menuOrderDto2);

        MenuOrders menuOrders = new MenuOrders(130L, "menu1", 1400.00);
        MenuOrders menuOrders2 = new MenuOrders(131L, "menu2", 1500.00);
        List<MenuOrders> menuOrdersList = Arrays.asList(menuOrders, menuOrders2);

        CreateOrderDto orderDto = new CreateOrderDto("username1", menuList);

        User user = new User(123L, "Firstname", "lastname", "username1", null, "ajeku@email.com", null, Instant.now(), Collections.emptySet(), true);


        lenient().when(userRepository.findByUsername(orderDto.getUsername()))
                .thenReturn(Optional.of(user));

        Orders orders = new Orders();
        orders.setOrderId(123L);
        orders.setUser(user);
        orders.setCreatedDate(new Date());
        orders.setTotalPrice(2900.00);
        orders.setOrders(menuOrdersList);


        lenient().when(orderRepository.save(Mockito.any())).thenReturn(orders);


        assertThat(orderRepository.save(orders)).isSameAs(orders);
        assertThat(orderRepository.save(orders)).isNotNull();
    }

    @Test
    @DisplayName("should get order by date")
    public void shouldGetOrderByDate() {

        OrderBYDateDto orderBYDateDto = new OrderBYDateDto("username1", "2022-11-07", "2022-11-07");
        User user = new User(123L, "Firstname", "lastname", "username1", null, "ajeku@email.com", null, Instant.now(), Collections.emptySet(), true);

        when(userRepository.findByUsername(orderBYDateDto.getUsername())).thenReturn(Optional.of(user));

        List<Tuple> tupleList = new ArrayList<>();

        when(orderRepository.getOrdersByDateRange(user.getUserId(),
                orderBYDateDto.getFirstDate(), orderBYDateDto.getSecondDate())).thenReturn(tupleList);

        ordersServiceImpl.getOrderByDate(orderBYDateDto);

        verify(orderRepository, times(1)).getOrdersByDateRange(user.getUserId(),
                orderBYDateDto.getFirstDate(), orderBYDateDto.getSecondDate());

    }

    @Test
    @DisplayName("should get recent orders by username")
    public void shouldGetRecentOrdersByUsername() {

        User user = new User(123L, "Firstname", "lastname", "username1", null, "ajeku@email.com", null, Instant.now(), Collections.emptySet(), true);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<Tuple> tupleList = new ArrayList<>();

        when(orderRepository.getRecentOrdersByUsername(user.getUserId())).thenReturn(tupleList);

        ordersServiceImpl.getRecentOrdersByUsername(user.getUsername());

        verify(orderRepository, times(1)).getRecentOrdersByUsername(user.getUserId());


    }

}
