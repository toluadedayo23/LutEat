package com.practise.luteat.dto;

import java.time.Instant;

public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
}
