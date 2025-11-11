package com.api.csm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cookie")
@Getter
@Setter
public class CookieProperties {
    private String name = "jwt_token";
    private boolean httpOnly = true;
    private boolean secure = false;
    private String sameSite = "Lax";
    private int maxAge = 3600;
    private String path = "/";
}

