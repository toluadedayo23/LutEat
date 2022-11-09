package com.practise.luteat.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.luteat.controller.MenuOrdersController;
import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.model.User;
import com.practise.luteat.security.AuthEntryPointJwt;
import com.practise.luteat.security.JwtUtils;
import com.practise.luteat.service.MenuOrderService;
import com.practise.luteat.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuOrdersController.class)
public class menuOrdersControllerTest {

    @MockBean
    private MenuOrderService menuOrderService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should get all menu-orders")
    public void shouldGetAllMenuOrders() throws Exception {
        MenuOrderDto menuOrderDto = new MenuOrderDto("menu1", 2000.00);

        MenuOrderDto menuOrderDto2 = new MenuOrderDto("menu2", 3000.00);


        Mockito.when(menuOrderService.getAllMenu()).thenReturn(Arrays.asList(menuOrderDto, menuOrderDto2));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/menu-orders"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("menu1")))
                .andExpect(jsonPath("$[0].price", is(2000.00)))
                .andExpect(jsonPath("$[1].name", is("menu2")))
                .andExpect(jsonPath("$[1].price", is(3000.00)));
    }


    @Test
    @DisplayName("should get update menu-orders")
    public void shouldUpdateMenuOrders() throws Exception {

        MenuOrderDto menuOrderDto = new MenuOrderDto("Refuel", 2000.00);

        MenuOrderDto updatedMenu = new MenuOrderDto("Refuel", 2500.00);

        Mockito.when(menuOrderService.updateMenuPrice(menuOrderDto)).thenReturn(updatedMenu);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/menu-orders/update"))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    @DisplayName("should get delete menu by name")
    public void shouldDeleteMenuOrdersByName() throws Exception {
        MenuOrderDto menuOrderDto = new MenuOrderDto("menu1", 2000.00);

        Mockito.doNothing().when(menuOrderService).deleteMenu(menuOrderDto.getName());


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/menu-orders/{name}", menuOrderDto.getName()))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @DisplayName("should  create menu-orders")
    public void shouldCreateMenuOrders() throws Exception {

        MenuOrderDto menuOrderDto = new MenuOrderDto("Refuel", 2000.00);

        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(menuOrderService.createMenu(menuOrderDto)).thenReturn(menuOrderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/menu-orders")
                .content(mapper.writeValueAsString(menuOrderDto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
