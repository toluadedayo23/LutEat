package com.practise.luteat.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.luteat.controller.OrdersController;
import com.practise.luteat.dto.*;
import com.practise.luteat.model.User;
import com.practise.luteat.security.AuthEntryPointJwt;
import com.practise.luteat.security.JwtUtils;
import com.practise.luteat.service.OrdersService;
import com.practise.luteat.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrdersController.class)
public class OrdersControllerTest {

    @MockBean
    private OrdersService ordersService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should get recent orders by username")
    public void shouldGetRecentOrdersByUsername() throws Exception {
        OrderByUsernameResponse orderByUsernameResponse = new OrderByUsernameResponse("order1", 2000.00, new Date());
        OrderByUsernameResponse orderByUsernameResponse2 = new OrderByUsernameResponse("order2", 3000.00, new Date());
        List<OrderByUsernameResponse> orderList = Arrays.asList(orderByUsernameResponse, orderByUsernameResponse2);
        User user = new User(123L, "Firstname", "lastname", "username1", null, "ajeku@email.com", null, Instant.now(), Collections.emptySet(), true);

        Mockito.when(ordersService.getRecentOrdersByUsername(user.getUsername())).thenReturn(orderList);
        ObjectMapper mapper = new ObjectMapper();


        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/get-recent-orders-by/{username}", "username1")
                        .content(mapper.writeValueAsString(orderList))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should create orders")
    public void shouldCreateOrders() throws Exception {

        MenuOrderDto menuOrderDto = new MenuOrderDto("Refuel", 2000.00);

        CreateOrderResponse createOrderResponse = new CreateOrderResponse("username", 2000.00, null, Arrays.asList(menuOrderDto));

        CreateOrderDto createOrderDto = new CreateOrderDto("username", Arrays.asList(menuOrderDto));


        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(ordersService.createOrder(createOrderDto)).thenReturn(createOrderResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/create-order")
                        .content(mapper.writeValueAsString(createOrderResponse))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should get orders by Date Range")
    public void shouldgetOrdersByDateRange() throws Exception {

        OrderBYDateDto orderBYDateDto = new OrderBYDateDto("username", "2002-11-09", "2002-11-09");

        OrderResponse orderResponse = new OrderResponse("menuname", 2000.00,new Date(), new BigInteger("2"));

        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(ordersService.getOrderByDate(orderBYDateDto)).thenReturn(Arrays.asList(orderResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/get-order-by-date")
                        .content(mapper.writeValueAsString(orderResponse))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }


}
