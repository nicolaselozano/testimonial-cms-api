package com.api.csm.auth;

import com.api.csm.config.properties.CookieProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieUseCase {

    private final CookieProperties props;

    public void addJwtCookie(HttpServletResponse response, String value) {
        ResponseCookie cookie = ResponseCookie.from(props.getName(), value)
                .path(props.getPath())
                .httpOnly(props.isHttpOnly())
                .secure(props.isSecure())
                .sameSite(props.getSameSite())
                .maxAge(props.getMaxAge())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken).
                path("/auth/refresh")
                .httpOnly(props.isHttpOnly())
                .secure(props.isSecure())
                .sameSite(props.getSameSite())
                .maxAge(7 * 24 * 60 * 60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/auth/refresh");
        response.addCookie(cookie);
    }


}
