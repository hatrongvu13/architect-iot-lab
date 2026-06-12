package com.htv.security.service;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenStore {
    void save(String tokenId, String userId, Instant expiresAt);
    Optional<String> findUserId(String tokenId);
    void revoke(String tokenId);
}
