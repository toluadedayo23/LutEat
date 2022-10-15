package com.practise.luteat.service;

import com.practise.luteat.dto.RegisterRequest;

public interface AuthService {
     void singUp(RegisterRequest registerRequest);
     void login();
     void verifyAccount();
     boolean isLoggedIn();

}
