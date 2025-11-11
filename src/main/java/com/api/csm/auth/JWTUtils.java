package com.api.csm.auth;

import com.api.csm.config.properties.SpringProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {

    private final SpringProperties springProperties;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(springProperties.getJwtKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UUID userId, List<String> roles) {
        long expirationMillis = Long.parseLong(springProperties.getJwtExpiration());

        log.info("Roles: {}", roles);
        List<String> authorities = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .toList();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("authorities", authorities)
                .issuer(springProperties.getJwtIssuer())
                .issuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey())
                .compact();
    }


    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);

            Date expiration = claims.getExpiration();
            if (expiration == null || expiration.before(new Date())) {
                return false;
            }

            if (!springProperties.getJwtIssuer().equals(claims.getIssuer())) {
                return false;
            }

            if (claims.getAudience() != null && !claims.getAudience().equals(springProperties.getJwtAudience())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return (List<String>) parseToken(token).get("authorities");
    }
}