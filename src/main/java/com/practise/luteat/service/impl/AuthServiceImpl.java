package com.practise.luteat.service.impl;

import com.practise.luteat.dto.RegisterRequest;
import com.practise.luteat.exceptions.EmailVerificationException;
import com.practise.luteat.exceptions.UsernameEmailExistsException;
import com.practise.luteat.model.User;
import com.practise.luteat.model.UserEmailVerification;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserEmailVerificationRepository userEmailVerificationRepository;
    private final PasswordEncoder passwordEncoder;

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
    public void login() {

    }


    @Override
    @Transactional
    public void verifyAccount(String token) {
        UserEmailVerification userEmailVerification = userEmailVerificationRepository.findById(token).orElseThrow(
                () -> new EmailVerificationException("Invalid Token"));
        if (Instant.now().getEpochSecond() - userEmailVerification.getExpiryDate().getEpochSecond() > 300) {
            userEmailVerificationRepository.delete(userEmailVerification);
            throw new EmailVerificationException("Verification Token Has expired, please request a new one");
        }
        fetchAndEnableAccount(userEmailVerification.getUsername());
        userEmailVerificationRepository.delete(userEmailVerification);
    }

    private void fetchAndEnableAccount(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            userEmailVerificationRepository.deleteByUsername(username);
            throw new EmailVerificationException("Account Activation failed, User with the username: " + username
                    + " does not exist");
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(true);
            userRepository.save(user);
        }

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
