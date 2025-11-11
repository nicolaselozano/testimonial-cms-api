package com.api.csm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test")
@Getter
@Setter
public class TestUserProperties {
    private String user;
    private String userPass;
}
