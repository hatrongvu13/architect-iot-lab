package com.htv.common.contract;

import java.time.Instant;

public record ApiResponse<T>(boolean success, T data, ApiError error, Instant timestamp, String correlationId) {
    public static <T> ApiResponse<T> ok(T data, String correlationId) {
        return new ApiResponse<>(true, data, null, Instant.now(), correlationId);
    }

    public static <T> ApiResponse<T> fail(ApiError error, String correlationId) {
        return new ApiResponse<>(false, null, error, Instant.now(), correlationId);
    }
}
