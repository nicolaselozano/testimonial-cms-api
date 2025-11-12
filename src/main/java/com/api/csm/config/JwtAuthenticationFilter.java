package com.api.csm.config;

import com.api.csm.auth.JWTUtils;
import com.api.csm.config.properties.CookieProperties;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final CookieProperties cookieProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);

        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtUtils.validateToken(token)) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no válido");
            return;
        }

        Claims claims = jwtUtils.parseToken(token);

        SecurityContextHolder.clearContext();

        setAuthentication(claims);
        log.debug("JWT válido para usuario: {} con roles {}",
                claims.getSubject(), claims.get("authorities"));

        filterChain.doFilter(request, response);

    }

    private void setAuthentication(Claims claims) {
        List<String> roles = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String extractToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieProperties.getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
