package com.practise.luteat.repositoryTest;

import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserRepository;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should check if User exists by username")
    @Sql("classpath:test-data.sql")
    public void shouldCheckIfUserExistsByUsername(){
       assertThat(userRepository.existsByUsername("testing1")).isTrue();
    }

    @Test
    @DisplayName("should check if user exists by email")
    @Sql("classpath:test-data.sql")
    public void shouldCheckIfEmailExistsByEmail(){
        assertThat(userRepository.existsByEmail("testuser1@email.com")).isTrue();
    }

    @Test
    @DisplayName("should find user by username")
    @Sql("classpath:test-data.sql")
    public void shouldFindUserByUsername(){
        Optional<User> user = userRepository.findByUsername("testing1");

        assertThat((user)).isPresent();
    }

    @Test
    @DisplayName("should find user by email")
    @Sql("classpath:test-data.sql")
    public void shouldFindUserByEmail(){
        Optional<User> user = userRepository.findByEmail("testuser1@email.com");

        assertThat((user)).isPresent();
    }

}
