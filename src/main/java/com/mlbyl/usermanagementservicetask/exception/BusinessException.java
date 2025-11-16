package com.mlbyl.usermanagementservicetask.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    public BusinessException(String message, String errorCode, Integer statusCode, HttpStatus status) {
        super(message, errorCode, statusCode, status);
    }
}
