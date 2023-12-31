package com.example.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserInfoRepository;
import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    
    @Value("${refresh.token.expiration.duration}")
    private int refreshTokenExpirationDuration;

    public RefreshToken createRefreshToken(String username) {
    	RefreshToken refreshToken = RefreshToken.builder()
    			.userInfo(userInfoRepository.findByName(username).get())
                .token(UUID.randomUUID().toString())
//                .expiryDate(Instant.now().plusMillis(1000 *60 * 10)) //10 min
                .expiryDate(Instant.now().plusSeconds(refreshTokenExpirationDuration))
                .build();
    	
        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    @Transactional
    public void deleteTokenByUserId(int userId) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);

        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteByUserId(userId);
        }
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
