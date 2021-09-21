package com.example.demo.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void saveConfirmationToken(TokenEntity token) {
        tokenRepository.save(token);
    }

    public Optional<TokenEntity> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public boolean updateConfirmedTime(String token) {
        var exist_t = tokenRepository.findByToken(token).
                orElseThrow(() -> new NoSuchElementException("token not found"));
        exist_t.setConfirmedTime(LocalDateTime.now());
        tokenRepository.save(exist_t);
        return true;
    }


}
