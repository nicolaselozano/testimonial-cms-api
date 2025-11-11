package com.api.csm.auth;

import com.api.csm.models.RefreshToken;
import com.api.csm.models.User;
import com.api.csm.utils.AesUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JWTUtils jwtUtils;
    private final CookieUseCase cookieUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {



        String refreshToken = extractRefreshToken(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token no encontrado");
        }

        Optional<RefreshToken> storedToken = refreshTokenUseCase.validateRefreshToken(refreshToken);
        if (storedToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido o expirado");
        }

        User user = storedToken.get().getUser();
        List<String> roles = user.getRoles().stream()
                .map(r -> "ROLE_" + r.getRole().name())
                .toList();

        String newAccessToken = createTokenWithRefreshToken(user,refreshToken);

        cookieUseCase.addJwtCookie(response, newAccessToken);

        return ResponseEntity.ok(Map.of("message", "Token renovado"));
    }


    private String createTokenWithRefreshToken(User userEntity, String refreshToken){

        RefreshToken entityRefreshToken = refreshTokenUseCase.validateRefreshToken(refreshToken).orElseThrow();

        if(userEntity.getId() != entityRefreshToken.getUser().getId()){
            log.error("El Id del Usuario no coincide : {}", entityRefreshToken.getUser().getId());
            return null;
        }

        List<String> roles = userEntity.getRoles().stream()
                .map(r -> "ROLE_" + r.getRole().name())
                .toList();

        return jwtUtils.generateToken(userEntity.getId(), roles);

    }

    private String extractRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken != null) {
            refreshTokenUseCase.revokeRefreshToken(refreshToken);
        }

        cookieUseCase.clearJwtCookie(response);
        cookieUseCase.clearRefreshTokenCookie(response);

        return ResponseEntity.ok(Map.of("message", "Sesión cerrada correctamente"));
    }

}
