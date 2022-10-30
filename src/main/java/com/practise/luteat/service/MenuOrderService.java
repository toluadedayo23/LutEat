package com.practise.luteat.service;

import com.practise.luteat.dto.MenuOrderDto;

import java.util.List;

public interface MenuOrderService {

    List<MenuOrderDto> getAllMenu();

    MenuOrderDto updateMenu(MenuOrderDto menuOrderDto);

    void deleteMenu(MenuOrderDto menuOrderDto);

    MenuOrderDto createMenu(MenuOrderDto menuOrderDto);

}
