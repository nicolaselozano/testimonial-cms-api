package com.api.csm.config;

import com.api.csm.auth.CookieService;
import com.api.csm.auth.CustomUserDetailService;
import com.api.csm.auth.JWTUtils;
import com.api.csm.auth.RefreshTokenService;
import com.api.csm.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtils jwtUtils;
    private final CookieService cookieService;
    private final CustomUserDetailService customUserDetailService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = oauthUser.getAttribute("email");
            String name = oauthUser.getAttribute("name");

            if (email == null || name == null) {
                throw new RuntimeException("Email or Name attribute is missing from OAuth2 provider");
            }

            User userEntity = customUserDetailService.findOrCreateOAuthUser(email,name);

            List<String> roles = userEntity.getRoles().stream()
                    .map(r -> "ROLE_" + r.getRole().name())
                    .toList();

            String token = jwtUtils.generateToken(userEntity.getId(), roles);
            String refreshToken = refreshTokenService.createRefreshToken(userEntity);

            cookieService.addJwtCookie(response, token);
            cookieService.addRefreshTokenCookie(response, refreshToken);

            log.info("JWT generado para userId={} email={}", userEntity.getId(), userEntity.getEmail());

            response.sendRedirect("/oauth2/success");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during OAuth2 success handling", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OAuth2 Login Failed");
        }
    }
}
