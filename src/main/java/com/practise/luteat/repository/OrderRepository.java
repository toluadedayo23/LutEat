package com.practise.luteat.repository;

import com.practise.luteat.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {


    @Query(value = " select MO.name, MO.price, O.created_date,\n" +
            "count(MO.name) as Number_of_Items_Per_Order\n" +
            "from menu_orders as MO\n" +
            "Join orders_menu_order as OMO\n" +
            "on MO.menu_order_id = OMO.menu_order_id\n" +
            "Join orders as O\n" +
            "on O.order_id = OMO.order_id\n" +
            "where O.user_id = :userId\n" +
            "and date(O.created_date) between :firstDate and :secondDate\n" +
            "group by MO.name\n" +
            "order by O.created_date desc\n" +
            "limit 10 ", nativeQuery = true)
    List<Tuple> getOrdersByDateRange(@Param("userId") Long userId,
                                     @Param("firstDate") String firstDate,
                                     @Param("secondDate") String secondDate);


    @Query(value = " select MO.name, MO.price, O.created_date\n" +
            "from menu_orders as MO\n" +
            "Join orders_menu_order as OMO\n" +
            "On MO.menu_order_id = OMO.menu_order_id\n" +
            "Join Orders as O\n" +
            "On OMO.order_id = O.order_id\n" +
            "where O.user_id = :userId\n" +
            "order by O.created_date desc\n" +
            "limit 10 ", nativeQuery = true)
    List<Tuple> getRecentOrdersByUsername(@Param("userId") Long userId);
}
