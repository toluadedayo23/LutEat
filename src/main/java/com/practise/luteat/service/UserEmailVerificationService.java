package com.practise.luteat.service;

import com.practise.luteat.dto.ResendVerificationDetailsDto;

public interface UserEmailVerificationService {

    void verifyAccount(String token);
    String generateVerificationTokenByUsername(String username);
    void resendVerificationLink(ResendVerificationDetailsDto resendVerificationDetailsDto);
}
