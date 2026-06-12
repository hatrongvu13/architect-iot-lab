package com.htv.security;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> publicPaths = new ArrayList<>(List.of("/actuator/heath", "/actuator/info", "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/refresh"));
    private List<AccessRule> accessRules = new ArrayList<>();
    private Mfa mfa = new Mfa();
    private Cors cors = new Cors();
    private Headers headers = new Headers();

    @Data
    public static class AccessRule {
        private String pattern;
        private HttpMethod method;
        private List<String> roles = new ArrayList<>();
        private List<String> permissions = new ArrayList<>();
    }

    @Data
    public static class Mfa {
        /**
         * Global switch: if false all MFA endpoints are disabled. If true, MFA is still opt-in user.
         */
        private boolean enabled = true;
        /**
         * MFA is not forced by role. It is applied only when user.mfaEnabled = true or an access-rule requires MFA.
         */
        private boolean userOptInOnly = true;
        private Duration challengeTtl = Duration.ofMinutes(5);
        private Duration setupTtl = Duration.ofMinutes(10);
        private int codeLength = 6;
        private int totpTimeStepSeconds = 30;
        private String issuerLabel = "HTV-Lab";
        private List<MfaMethod> allowedMfaMethods = new ArrayList<>(List.of(MfaMethod.TOTP_QR, MfaMethod.TOTP_QR));
        private Email email = new Email();
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

    public enum MfaMethod {
        TOTP_QR, EMAIL_OTP
    }

    @Data
    public static class Email {
        private String from = "no-reply@htv.com.vn";
        private String subject = "Your Htv Lab verification code";
        private String template = "Your verification code is: {{code}}. It expires in {{ttlMinutes}} minutes.";
    }
}