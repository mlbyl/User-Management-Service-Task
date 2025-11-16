package com.mlbyl.usermanagementservicetask.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseException {
    public ValidationException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, 400, status);
    }
}
