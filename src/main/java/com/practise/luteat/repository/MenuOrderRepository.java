package com.practise.luteat.repository;

import com.practise.luteat.model.MenuOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrderRepository extends JpaRepository<MenuOrders, Long> {
}
