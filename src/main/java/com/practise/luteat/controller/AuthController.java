package com.practise.luteat.controller;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.event.UserRegistrationEvent;
import com.practise.luteat.model.User;
import com.practise.luteat.service.AuthService;
import com.practise.luteat.service.UserEmailVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserEmailVerificationService userEmailVerificationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest){
        User user = authService.singUp(registerRequest);
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(user));
        return ResponseEntity.status(HttpStatus.OK).body("User Registration Successful");
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token){
        userEmailVerificationService.verifyAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Account Verification Successful");
    }

}
