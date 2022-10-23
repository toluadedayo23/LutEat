package com.practise.luteat.service.impl;

import com.practise.luteat.dto.AuthenticationResponse;
import com.practise.luteat.dto.LoginRequest;
import com.practise.luteat.dto.RefreshTokenRequest;
import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.exceptions.UsernameEmailExistsException;
import com.practise.luteat.listener.EmailSender;
import com.practise.luteat.model.User;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.security.JwtProvider;
import com.practise.luteat.service.AuthService;
import lombok.AllArgsConstructor;
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
    private final UserEmailVerificationRepository userEmailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final UserEmailVerificationServiceImpl userEmailVerificationService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public User singUp(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameEmailExistsException("Username: " + registerRequest.getUsername() + " already exists, please choose a new one");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UsernameEmailExistsException("Email: " + registerRequest.getEmail() + " already exists, please choose a new one");
        }

        User user = new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setPhonenumber(registerRequest.getPhonenumber());
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        return userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
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
        return userRepository.findByUsername(currenUser.getSubject()).orElseThrow( () ->
                new UsernameNotFoundException("User with the username: " +
                        "" + currenUser.getSubject().toUpperCase() + " not found")
        );
    }


//    @Override
//    @Transactional
//    public void verifyAccount(String token) {
//        UserEmailVerification userEmailVerification = userEmailVerificationRepository.findById(token).orElseThrow(
//                () -> new EmailVerificationException("Invalid Token"));
//        if (Instant.now().getEpochSecond() - userEmailVerification.getExpiryDate().getEpochSecond() > 300) {
//            userEmailVerificationRepository.delete(userEmailVerification);
//            throw new EmailVerificationException("Verification Token Has expired, please request a new one");
//        }
//        fetchAndEnableAccount(userEmailVerification.getUsername());
//        userEmailVerificationRepository.delete(userEmailVerification);
//    }
//
//    private void fetchAndEnableAccount(String username) {
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isEmpty()) {
//            userEmailVerificationRepository.deleteByUsername(username);
//            throw new EmailVerificationException("Account Activation failed, User with the username: " + username
//                    + " does not exist");
//        }
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setEnabled(true);
//            userRepository.save(user);
//        }
//
//    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }


//    @Override
//    public void resendVerificationLink(ResendVerificationDetailsDto resendVerificationDetailsDto) {
//        Optional<User> userOptional = userRepository.findByEmail(resendVerificationDetailsDto.getEmail());
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            if(user.getUsername() == resendVerificationDetailsDto.getUsername()){
//                emailSender.sendMail(user.getEmail(),
//                        userEmailVerificationService.generateVerificationTokenByUsername(user.getUsername())
//                );
//            }
//        }
//    }
}
