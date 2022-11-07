package com.practise.luteat.service;

import com.practise.luteat.dto.*;
import com.practise.luteat.model.Role;
import com.practise.luteat.model.User;

import java.util.Collection;

public interface AuthService {
     void singUp(signupRequest registerRequest);
     AuthenticationResponse login(LoginRequest loginRequest);
     AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
     User getCurrentUser();
     boolean isLoggedIn();

}
