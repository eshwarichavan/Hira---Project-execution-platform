package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.entities.RefreshToken;
import com.example.projectexecutionplatform.repositories.RefreshTokenRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    //create refresh token
    public RefreshToken createRefreshToken(Long userId){
        var token=new RefreshToken();
        token.setUser(userRepository.findById(userId).get());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(token);
    }


    //token expired
    public boolean isTokenExpired(RefreshToken token){
        return token.getExpiryDate().isBefore(Instant.now());
    }
}


