package com.practise.luteat.repository;

import com.practise.luteat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
