package com.practise.luteat.controller;

import com.practise.luteat.dto.*;
import com.practise.luteat.service.AuthService;
import com.practise.luteat.service.UserEmailVerificationService;
import com.practise.luteat.service.impl.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserEmailVerificationService userEmailVerificationService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody signupRequest signupRequest) {
        authService.singUp(signupRequest);
        return ResponseEntity.status(OK).body("User Registration Successful");
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
        userEmailVerificationService.verifyAccount(token);
        return ResponseEntity.status(OK).body("Account Verification Successful");
    }

    @PostMapping("/resendVerificationEmail")
    public ResponseEntity<String> resendVerificationEmail(@Valid @RequestBody ResendVerificationDetailsDto resendVerificationDetailsDto) {
        userEmailVerificationService.resendVerificationLink(resendVerificationDetailsDto);
        return ResponseEntity.status(OK).body("Verification Email has been resent");
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refreshToken")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("User logout!");
    }
}
