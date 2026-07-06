package com.htv.security.web;

import com.htv.common.contract.ApiError;
import com.htv.common.contract.ApiResponse;
import com.htv.common.error.ErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(ErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleErrorException(ErrorException error, HttpServletRequest request) {
        return ApiResponse.fail(new ApiError(error.errorCode().name(), error.getMessage(), List.of()), request.getHeader("X-Correlation-Id"));
    }
}
