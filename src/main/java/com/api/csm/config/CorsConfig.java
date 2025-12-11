package com.api.csm.config;

import com.api.csm.config.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final AppProperties appProperties;

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration publicConfig = new CorsConfiguration();
        publicConfig.addAllowedOriginPattern("*");
        publicConfig.addAllowedMethod("*");
        publicConfig.addAllowedHeader("*");
        publicConfig.setAllowCredentials(false);

        source.registerCorsConfiguration("/api/testimonials/public/**", publicConfig);

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(appProperties.getCorsOrigins());
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Set-Cookie"));

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
