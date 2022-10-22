package com.practise.luteat.service.impl;

import com.practise.luteat.exceptions.EmailVerificationException;
import com.practise.luteat.model.User;
import com.practise.luteat.model.UserEmailVerification;
import com.practise.luteat.repository.UserEmailVerificationRepository;
import com.practise.luteat.repository.UserRepository;
import com.practise.luteat.service.UserEmailVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserEmailVerificationServiceImpl implements UserEmailVerificationService {

    private final UserEmailVerificationRepository userEmailVerificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public String generateVerificationTokenByUsername(String username){
        if(!userEmailVerificationRepository.existsByUsername(username)){
            UserEmailVerification emailVerification = new UserEmailVerification(username);
            emailVerification.setExpiryDate(Instant.now());
            emailVerification = userEmailVerificationRepository.save(emailVerification);
            return emailVerification.getToken();
        }

        return getVerificationTokenByUsername(username);
    }

    private String getVerificationTokenByUsername(String username){
        UserEmailVerification emailVerification = userEmailVerificationRepository.findByUsername(username)
                .orElseThrow(() -> new EmailVerificationException("Username does not exist"));
        return emailVerification.getToken();
    }

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



}
