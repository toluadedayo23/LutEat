package com.practise.luteat.service;

public interface UserEmailVerificationService {

    void verifyAccount(String token);
    String generateVerificationTokenByUsername(String username);
}
