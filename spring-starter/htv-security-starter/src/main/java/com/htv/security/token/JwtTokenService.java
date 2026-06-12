package com.htv.security.token;

import com.htv.security.HtvSecurityProperties;
import com.htv.security.model.AuthDtos;
import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RefreshTokenStore refreshTokenStore;
    private final HtvSecurityProperties properties;

    public AuthDtos.TokenResponse issueTokenPair(AuthenticatedUser user, boolean mfaVerified) {
        Instant now = Instant.now();
        Instant accessExpiresAt = now.plus(properties.getAccessTokenTtl());
        String refreshTokenId = UUID.randomUUID().toString();
        Instant refreshExpiresAt = now.plus(properties.getRefreshTokenTtl());
        refreshTokenStore.save(refreshTokenId, user.userId(), refreshExpiresAt);

        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                .issuer(properties.getIssuer())
                .issuedAt(now)
                .expiresAt(accessExpiresAt)
                .subject(user.userId())
                .id(UUID.randomUUID().toString())
                .claim(properties.getTokenTypeClaim(), "access")
                .claim("username", user.username())
                .claim("email", user.email())
                .claim(properties.getRoleClaim(), user.roles())
                .claim(properties.getPermissionClaim(), user.permissions())
                .claim(properties.getMfaClaim(), mfaVerified)
                .build();

        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                .issuer(properties.getIssuer())
                .issuedAt(now)
                .expiresAt(refreshExpiresAt)
                .subject(user.userId())
                .id(refreshTokenId)
                .claim(properties.getTokenTypeClaim(), "refresh")
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

        return new AuthDtos.TokenResponse(accessToken, refreshToken, "Bearer", properties.getAccessTokenTtl().toSeconds(), user.userId(), user.roles(), user.permissions(), mfaVerified);
    }

    public Jwt validate(String token) {
        return jwtDecoder.decode(token);
    }

    public String refreshTokenId(String refreshToken) {
        Jwt jwt = validate(refreshToken);
        Object typ = jwt.getClaim(properties.getTokenTypeClaim());
        if (!"refresh".equalsIgnoreCase(String.valueOf(typ)))
            throw new JwtException("Invalid token type");

        return jwt.getId();
    }
}
