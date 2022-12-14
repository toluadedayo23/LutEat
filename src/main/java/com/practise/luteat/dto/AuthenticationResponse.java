package com.practise.luteat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
}
