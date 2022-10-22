package com.practise.luteat.service.impl;

import com.practise.luteat.dto.RefreshTokenRequest;
import com.practise.luteat.exceptions.RefreshTokenException;
import com.practise.luteat.model.RefreshToken;
import com.practise.luteat.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(RefreshTokenRequest refreshTokenRequest){
        refreshTokenRepository.findById(refreshTokenRequest.getRefreshToken()).orElseThrow(
                () ->     new RefreshTokenException("Invalid Refresh Token")
        );
    }

    @Transactional
    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteById(token);
    }


}
