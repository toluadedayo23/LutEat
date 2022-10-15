package com.practise.luteat.mapper;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "enabled", expression = "java(setToFalse())")
    User map(RegisterRequest registerRequest);

    default Boolean setToFalse(){
        return false;
    }
}
