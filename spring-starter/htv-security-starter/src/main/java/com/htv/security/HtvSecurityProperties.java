package com.htv.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "htv.security")
public class HtvSecurityProperties {
    private boolean enabled = true;
    private String issuer = "htv";
    /**
     * Minimum recommended length for HS256 secret is 32 bytes. Replace in each environment.
     */
    private String jwtSecret = "change-me-change-me-change-me-change-me";
    private Duration accessTokenTtl = Duration.ofMinutes(15); // TTL default 15 minutes
    private Duration refreshTokenTtl = Duration.ofDays(7); // TTL default refresh token 7 days
    private String roleClaim = "roles";
    private String permissionClaim = "permissions";
    private String tokenTypeClaim = "typ";
    private String mfaClaim = "mfa";
    private List<String> publicPaths = new ArrayList<>(List.of("/actuator/heath", "/actuator/info", "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/refresh", "/api/v1/auth/ping"));
    private List<AccessRule> accessRules = new ArrayList<>();
    private Cors cors = new Cors();
    private Headers headers = new Headers();

    @Data
    public static class AccessRule {
        private String pattern;
        private HttpMethod method;
        private Set<String> roles = new HashSet<>();
        private Set<String> permissions = new HashSet<>();
        private boolean requireMfa = false;
    }

    @Data
    public static class Cors {
        private boolean enabled = true;
        private List<String> allowedOrigins = new ArrayList<>(List.of("*"));
        private List<String> allowedMethods = new ArrayList<>(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        private List<String> allowedHeaders = new ArrayList<>(List.of("Authorization", "Content-Type", "X-Correlation-Id"));

    }

    @Data
    public static class Headers {
        private boolean contentSecurityPolicy = true;
        private String cspPolicy = "default-src 'self'; frame-ancestors 'none'";
    }
}