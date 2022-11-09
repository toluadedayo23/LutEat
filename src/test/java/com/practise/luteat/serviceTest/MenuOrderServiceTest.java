package com.practise.luteat.serviceTest;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.mapper.MenuOrdersMapper;
import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.repository.MenuOrderRepository;
import com.practise.luteat.service.impl.MenuOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuOrderServiceTest {
    @Mock
    private MenuOrderRepository menuOrderRepository;

    @Mock
    private MenuOrdersMapper menuOrdersMapper;

    @Captor
    ArgumentCaptor<MenuOrders> menuOrdersArgumentCaptor;

    @Mock
    private MenuOrderServiceImpl menuOrderServiceImpl;

    @BeforeEach
    public void setUp() {
        menuOrderServiceImpl = new MenuOrderServiceImpl(menuOrderRepository, menuOrdersMapper);
    }

    @Test
    @DisplayName("should get all menu")
    public void shouldGetAllMenu() {
        MenuOrders menuOrders = new MenuOrders(123L, "test1", 1300.00);

        MenuOrders menuOrders2 = new MenuOrders(124L, "test2", 1400.00);

        MenuOrders menuOrders3 = new MenuOrders(125L, "test3", 1500.00);

        List<MenuOrderDto> menuOrderDtoList = Arrays.asList(menuOrdersMapper.mapMenuToDto(menuOrders),
                menuOrdersMapper.mapMenuToDto(menuOrders2),
                menuOrdersMapper.mapMenuToDto(menuOrders3));

        Mockito.when(menuOrderRepository.findAll().stream()
                        .map(menuOrdersMapper::mapMenuToDto)
                        .collect(Collectors.toList()))
                .thenReturn(menuOrderDtoList);

        List<MenuOrderDto> actualDto = menuOrderServiceImpl.getAllMenu();

        verify(menuOrderRepository, times(1)).findAll();
        assertThat(actualDto.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("should update menu")
    public void shouldUpdateMenu() {
        MenuOrderDto menuOrderDto = new MenuOrderDto("test1", 1400.00);

        MenuOrders menuOrders = new MenuOrders(123L, "test1", 1300.00);

        when(menuOrderRepository.findByName(menuOrderDto.getName())).thenReturn(Optional.of(menuOrders));

        menuOrders.setPrice(menuOrderDto.getPrice());


        menuOrderServiceImpl.updateMenuPrice(menuOrderDto);

        verify(menuOrderRepository, times(1)).save(menuOrdersArgumentCaptor.capture());

        assertThat(menuOrdersArgumentCaptor.getValue().getMenuOrderId()).isEqualTo(123L);
        assertThat(menuOrdersArgumentCaptor.getValue().getName()).isEqualTo("test1");
        assertThat(menuOrdersArgumentCaptor.getValue().getPrice()).isEqualTo(1400.00);

    }

    @Test
    @DisplayName("Should delete menu")
    public void shouldDeleteMenu() {

        MenuOrders menuOrders = new MenuOrders(123L, "test1", 1400.00);

        when(menuOrderRepository.findByName(menuOrders.getName())).thenReturn(Optional.of(menuOrders));

        menuOrderServiceImpl.deleteMenu(menuOrders.getName());

        verify(menuOrderRepository, times(1)).delete(menuOrders);
    }

    @Test
    @DisplayName("should create menu")
    public void shouldCreateMenu() {
        MenuOrderDto menuOrderDto = new MenuOrderDto("test1", 1400.00);

        when(menuOrderRepository.existsByName(menuOrderDto.getName())).thenReturn(false);

        menuOrderServiceImpl.createMenu(menuOrderDto);

        verify(menuOrderRepository, times(1)).save(menuOrdersMapper.map(menuOrderDto));

    }
}