package com.practise.luteat.service.impl;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.exceptions.MenuOrderException;
import com.practise.luteat.mapper.MenuOrdersMapper;
import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.repository.MenuOrderRepository;
import com.practise.luteat.service.MenuOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuOrderServiceImpl implements MenuOrderService {

    private final MenuOrderRepository menuOrderRepository;
    private final MenuOrdersMapper menuOrdersMapper;

    @Transactional(readOnly = true)
    @Override
    public List<MenuOrderDto> getAllMenu() {
       return menuOrderRepository.findAll().stream()
                .map(menuOrdersMapper::mapMenuToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MenuOrderDto UpdateMenu(MenuOrderDto menuOrderDto) {
        MenuOrders menuOrders= menuOrderRepository.findByName(menuOrderDto.getName())
                .orElseThrow(() -> new MenuOrderException("Menu doesn't exist, please find the right name and try again"));
        menuOrders.setName(menuOrderDto.getName());
        menuOrders.setPrice(menuOrders.getPrice());
        return menuOrdersMapper.mapMenuToDto(
                menuOrderRepository.save(menuOrders)
        );
    }

    @Transactional
    @Override
    public void DeleteMenu(MenuOrderDto menuOrderDto) {
        MenuOrders menuOrders = menuOrderRepository.findByName(menuOrderDto.getName())
                .orElseThrow(() -> new MenuOrderException("Menu doesn't exist, please find the right name and try again"));
        menuOrderRepository.delete(menuOrders);
    }

    @Override
    public MenuOrderDto createMenu(MenuOrderDto menuOrderDto) {
        if(menuOrderRepository.existsByName(menuOrderDto.getName())){
            throw new MenuOrderException("Menu already exists, please create a new one; or try update?");
        }

      return menuOrdersMapper.mapMenuToDto(
              menuOrderRepository.save(
                menuOrdersMapper.map(menuOrderDto)
        )
      );
    }
}
