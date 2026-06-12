package com.htv.common.core;

public record Result<T>(boolean success, T data, String errorCode, String message) {
    public static <T> Result<T> ok(T data) {
        return new Result<>(true, data, null, null);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(false, null, code, message);
    }
}