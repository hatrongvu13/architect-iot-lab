package com.htv.security.support;

import com.htv.security.service.RefreshTokenStore;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRefreshTokenStore implements RefreshTokenStore {
    private record Entry(String userId, Instant expiresAt) {
    }

    private final ConcurrentHashMap<String, Entry> store = new ConcurrentHashMap<>();

    @Override
    public void save(String tokenId, String userId, Instant expiresAt) {
        store.put(tokenId, new Entry(userId, expiresAt));
    }

    @Override
    public Optional<String> findUserId(String tokenId) {
        Entry entry = store.get(tokenId);
        if (entry == null || entry.expiresAt.isBefore(Instant.now())) return Optional.empty();
        return Optional.of(entry.userId);
    }

    @Override
    public void revoke(String tokenId) {
        store.remove(tokenId);
    }
}
