package com.practise.luteat.service;

import com.practise.luteat.exceptions.UserEmailVerificationException;
import com.practise.luteat.model.UserEmailVerification;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserEmailVerificationService {

    private final UserEmailVerificationRepository userEmailVerificationRepository;

    public String generateVerificationTokenByUsername(String username){
        if(!userEmailVerificationRepository.existsByUsername(username)){
            UserEmailVerification emailVerification = new UserEmailVerification(username);
            emailVerification.setExpiryDate(Instant.now());
            emailVerification = userEmailVerificationRepository.save(emailVerification);
            return emailVerification.getToken();
        }

        return getVerificationTokenByUsername(username);
    }

    private  String getVerificationTokenByUsername(String username){
        UserEmailVerification emailVerification = userEmailVerificationRepository.findByUsername(username)
                .orElseThrow(() -> new UserEmailVerificationException("Username does not exist"));
        return emailVerification.getToken();
    }



}
