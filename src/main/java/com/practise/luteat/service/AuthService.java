package com.practise.luteat.service;

import com.practise.luteat.dto.*;
import com.practise.luteat.model.User;

public interface AuthService {
     User singUp(RegisterRequest registerRequest);
     AuthenticationResponse login(LoginRequest loginRequest);
     AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);

     boolean isLoggedIn();
//     void verifyAccount(String token);
//     void resendVerificationLink(ResendVerificationDetailsDto resendVerificationDetailsDto);
}
