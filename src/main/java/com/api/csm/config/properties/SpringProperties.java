package com.api.csm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring")
@Getter
@Setter
public class SpringProperties {

    private String jwtKey;
    private String jwtExpiration;
    private String jwtIssuer = "csm-backend";
    private String jwtAudience = "csm-client";
}
