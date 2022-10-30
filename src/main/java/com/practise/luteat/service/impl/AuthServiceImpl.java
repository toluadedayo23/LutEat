package com.practise.luteat.service.impl;

import com.practise.luteat.dto.AuthenticationResponse;
import com.practise.luteat.dto.LoginRequest;
import com.practise.luteat.dto.RefreshTokenRequest;
import com.practise.luteat.dto.signupRequest;
import com.practise.luteat.event.UserRegistrationEvent;
import com.practise.luteat.exceptions.UsernameEmailExistsException;
import com.practise.luteat.listener.EmailSender;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.security.JwtProvider;
import com.practise.luteat.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void singUp(signupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UsernameEmailExistsException("Username: " + signupRequest.getUsername() + " already exists, please choose a new one");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UsernameEmailExistsException("Email: " + signupRequest.getEmail() + " already exists, please choose a new one");
        }

        User userObject = new User();
        userObject.setFirstname(signupRequest.getFirstname());
        userObject.setLastname(signupRequest.getLastname());
        userObject.setUsername(signupRequest.getUsername());
        userObject.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userObject.setEmail(signupRequest.getEmail());
        userObject.setPhonenumber(signupRequest.getPhonenumber());
        userObject.setCreatedDate(Instant.now());
        userObject.setEnabled(false);

        applicationEventPublisher.publishEvent(new UserRegistrationEvent(userObject));
        userRepository.save(userObject);
    }


    @Override
    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest);
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    @Override
    public User getCurrentUser() {
        Jwt currenUser = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(currenUser.getSubject()).orElseThrow(() ->
                new UsernameNotFoundException("User with the username: " +
                        "" + currenUser.getSubject().toUpperCase() + " not found")
        );
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }

}
