package com.practise.luteat.repository;

import com.practise.luteat.model.ERole;
import com.practise.luteat.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNames(ERole role);
}
