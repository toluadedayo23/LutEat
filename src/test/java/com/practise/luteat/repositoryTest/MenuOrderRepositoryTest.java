package com.practise.luteat.repositoryTest;

import com.practise.luteat.model.MenuOrders;
import com.practise.luteat.repository.MenuOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class MenuOrderRepositoryTest {

    @Autowired
    private MenuOrderRepository menuOrderRepository;

    @Test
    @DisplayName("should find menu-order by name")
    @Sql("classpath:test-data.sql")
    public void shouldFindMenuOrderByName(){

        Optional<MenuOrders> foundMenu = menuOrderRepository.findByName("menuTest");

        assertThat(foundMenu).isNotEmpty();
    }

    @Test
    @DisplayName("should check if menu exists by name")
    @Sql("classpath:test-data.sql")
    public void shouldCheckIfMenuExistsByName() {

        assertThat(menuOrderRepository.existsByName("menuTest")).isTrue();

    }



}
