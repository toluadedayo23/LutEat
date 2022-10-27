package com.practise.luteat.service;

import com.practise.luteat.dto.MenuOrderDto;

import java.util.List;

public interface MenuOrderService {

    List<MenuOrderDto> getAllMenu();

    MenuOrderDto UpdateMenu(MenuOrderDto menuOrderDto);

    void DeleteMenu(MenuOrderDto menuOrderDto);

    MenuOrderDto createMenu(MenuOrderDto menuOrderDto);

}
