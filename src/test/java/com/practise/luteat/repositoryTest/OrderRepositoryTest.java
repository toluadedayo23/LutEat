package com.practise.luteat.repositoryTest;

import com.practise.luteat.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.Tuple;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("should get orders by date range")
    @Sql("classpath:test-data.sql")
    public void shouldGetOrdersByDateRange() {
        List<Tuple> resultSet = orderRepository.getOrdersByDateRange(1L, "2022-11-08","2022-11-09");

        assertThat(resultSet).isNotNull();

    }

    @Test
    @DisplayName("Should get recent orders by username")
    @Sql("classpath:test-data.sql")
    public void shouldGetRecentOrderByUsername(){
        List<Tuple> resultSet = orderRepository.getRecentOrdersByUsername(1L);
        assertThat(resultSet).isNotNull();
    }
}
