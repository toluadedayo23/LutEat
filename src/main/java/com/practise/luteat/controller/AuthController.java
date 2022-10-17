package com.practise.luteat.controller;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.event.UserRegistrationEvent;
import com.practise.luteat.model.User;
import com.practise.luteat.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest){
        User user = authService.singUp(registerRequest);
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(user));
        return ResponseEntity.status(HttpStatus.OK).body("User Registration Successful");
    }
}
