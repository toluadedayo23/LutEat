package com.practise.luteat.service.impl;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.exceptions.UsernameEmailExistsException;
import com.practise.luteat.mapper.RegisterRequestMapper;
import com.practise.luteat.model.UserEmailVerification;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RegisterRequestMapper registerRequestMapper;
    private final UserEmailVerificationRepository userEmailVerificationRepository;

    @Override
    @Transactional
    public void singUp(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UsernameEmailExistsException("Username: " + registerRequest.getUsername() + " already exists, please choose a new one");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UsernameEmailExistsException("Email: " + registerRequest.getEmail() + " already exists, please choose a new one");
        }

       userRepository.save(registerRequestMapper.map(registerRequest));

    }


    @Override
    public void login() {

    }

    @Override
    public void verifyAccount() {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
