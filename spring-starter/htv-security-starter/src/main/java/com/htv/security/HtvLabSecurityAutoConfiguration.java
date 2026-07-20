package com.htv.security;

import com.htv.security.authorization.AccessRuleAuthorizationManager;
import com.htv.security.service.MfaService;
import com.htv.security.service.RefreshTokenStore;
import com.htv.security.service.UserMfaPreferenceService;
import com.htv.security.support.DisableMfaService;
import com.htv.security.support.InMemoryRefreshTokenStore;
import com.htv.security.token.HtvLabAuthenticationConverter;
import com.htv.security.token.JwtTokenService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@AutoConfiguration
@EnableMethodSecurity
@ConditionalOnClass(HttpSecurity.class)
@EnableConfigurationProperties(HtvSecurityProperties.class)
@ConditionalOnProperty(prefix = "htv.security", name = "enabled", havingValue = "true", matchIfMissing = false)
public class HtvLabSecurityAutoConfiguration {
    // default config of security common starter
    @Bean
    @ConditionalOnMissingBean
    public SecretKey htvLabSecuritySecretKey(HtvSecurityProperties properties) {
        return new SecretKeySpec(properties.getJwtSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public RefreshTokenStore refreshTokenStore() {
        return new InMemoryRefreshTokenStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public MfaService mfaService() {
        return new DisableMfaService();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserMfaPreferenceService userMfaPreferenceService() {
        return new UserMfaPreferenceService() {
            @Override
            public void enableMfa(String userId, HtvSecurityProperties.MfaMethod method) {
            }

            @Override
            public void disableMfa(String userId) {
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenService jwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, RefreshTokenStore store, HtvSecurityProperties properties) {
        return new JwtTokenService(jwtEncoder, jwtDecoder, store, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HtvSecurityProperties properties) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        if (properties.getCors().isEnabled()) {
//            http.csrf(Customizer.withDefaults());
            http.cors(cors -> cors.configurationSource(corsConfigurationSource(properties)));
        }
        http.headers(headers -> {
            headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::deny);
            if (properties.getHeaders().isContentSecurityPolicy()) {
                headers.contentSecurityPolicy(csp -> csp.policyDirectives(properties.getHeaders().getCspPolicy()));
            }
        });
        http.authorizeHttpRequests(auth -> {
            properties.getPublicPaths().forEach(path -> auth.requestMatchers(path).permitAll());
            for (HtvSecurityProperties.AccessRule accessRule : properties.getAccessRules()) {
                var manager = new AccessRuleAuthorizationManager(
                        accessRule.getRoles(),
                        accessRule.getPermissions(),
                        accessRule.isRequireMfa(),
                        properties.getMfaClaim()
                );

                if (accessRule.getMethod() == null) {
                    auth.requestMatchers(accessRule.getPattern()).access(manager);
                } else {
                    auth.requestMatchers(accessRule.getMethod(), accessRule.getPattern()).access(manager);
                }
            }
            auth.anyRequest().authenticated();
        });

        http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(new HtvLabAuthenticationConverter(properties));
            });
        });
        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsConfigurationSource corsConfigurationSource(HtvSecurityProperties properties) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(properties.getCors().getAllowedOrigins());
        configuration.setAllowedMethods(properties.getCors().getAllowedMethods());
        configuration.setAllowedHeaders(properties.getCors().getAllowedHeaders());
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
