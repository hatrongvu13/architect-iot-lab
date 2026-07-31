package com.htv.security;

import com.htv.security.authorization.AccessRuleAuthorizationManager;
import com.htv.security.token.HtvLabAuthenticationConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@AutoConfiguration
@ConditionalOnClass({
        HttpSecurity.class,
        SecurityFilterChain.class
})
@EnableConfigurationProperties(HtvSecurityProperties.class)
@EnableWebSecurity
public class HtvLabSecurityWebAutoConfiguration {
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
                        accessRule.getPermissions()
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
