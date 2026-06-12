package com.htv.security;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(HtvSecurityProperties.class)
@ConditionalOnProperty(prefix = "htv.security", name = "enabled", havingValue = "true", matchIfMissing = false)
public class HtvLabSecurityAutoConfiguration {
    // default config of security common starter
}
