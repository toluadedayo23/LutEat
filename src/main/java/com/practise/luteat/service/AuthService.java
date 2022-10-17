package com.practise.luteat.service;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.model.User;

public interface AuthService {
     User singUp(RegisterRequest registerRequest);
     void login();
     void verifyAccount(String token);
     boolean isLoggedIn();

}
