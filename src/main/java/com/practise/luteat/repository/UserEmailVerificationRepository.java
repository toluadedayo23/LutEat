package com.practise.luteat.repository;

import com.practise.luteat.model.UserEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmailVerificationRepository extends JpaRepository<UserEmailVerification, String> {

    boolean existsByUsername(String username);

    Optional<UserEmailVerification> findByUsername(String username);

    void deleteByUsername(String username);
}
