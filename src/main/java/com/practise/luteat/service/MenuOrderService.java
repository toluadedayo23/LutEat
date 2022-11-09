package com.practise.luteat.service;

import com.practise.luteat.dto.MenuOrderDto;

import java.util.List;

public interface MenuOrderService {

    List<MenuOrderDto> getAllMenu();

    MenuOrderDto updateMenuPrice(MenuOrderDto menuOrderDto);

    void deleteMenu(String name);

    MenuOrderDto createMenu(MenuOrderDto menuOrderDto);

}
