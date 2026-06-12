package com.htv.common.contract;

import java.util.List;

public record ApiError(String code, String message, List<FieldError> fields) {
}
