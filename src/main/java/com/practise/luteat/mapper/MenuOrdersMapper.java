package com.practise.luteat.mapper;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.model.MenuOrders;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuOrdersMapper {

    @Mapping(target = "name", source = "menuOrders.name")
    @Mapping(target ="price", source = "menuOrders.price")
    MenuOrderDto mapMenuToDto(MenuOrders menuOrders);

    @InheritInverseConfiguration
    @Mapping(target = "menuOrderId", ignore = true)
    MenuOrders map(MenuOrderDto menuOrderDto);
}
