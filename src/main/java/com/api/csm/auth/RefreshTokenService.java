package com.api.csm.auth;

import com.api.csm.models.RefreshToken;
import com.api.csm.models.User;
import com.api.csm.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusDays(7);

        RefreshToken entity = new RefreshToken();
        entity.setToken(token);
        entity.setUser(user);
        entity.setExpiryDate(expiry);

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUser_Id(user.getId());
        if(refreshToken.isPresent()){
            entity.setId(refreshToken.get().getId());
            refreshTokenRepository.save(entity);
        }else {
            refreshTokenRepository.save(entity);
        }
        return token;
    }

    public Optional<RefreshToken> validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(LocalDateTime.now()));
    }

    public void revokeRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
