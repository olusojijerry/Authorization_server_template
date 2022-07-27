package com.work.authentication.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("encryption.key-store")
@Data
public class KeyStoreConfig {
    private String alias;
    private String location;
    private String password;
    private String secret;
    private String classpathLoc;
}
