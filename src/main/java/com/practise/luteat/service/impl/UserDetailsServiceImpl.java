package com.practise.luteat.service.impl;

import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(loginAttemptService.isBlocked(username)){
            throw new LockedException("User account is locked, please try to login again after 24 hours");
        }

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No User " +
                        "Found with the Username: " + username)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getEnabled(),
                true, true, true,
                getAuthorities("USER")
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
