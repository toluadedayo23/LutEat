package com.practise.luteat.repository;

import com.practise.luteat.dto.MenuOrderDto;
import com.practise.luteat.model.MenuOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuOrderRepository extends JpaRepository<MenuOrders, Long> {
    Optional<MenuOrders> findByName(String name);

    boolean existsByName(String name);
}
