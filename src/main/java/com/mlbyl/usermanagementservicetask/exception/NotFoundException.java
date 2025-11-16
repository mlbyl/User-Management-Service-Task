package com.mlbyl.usermanagementservicetask.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(String message, String errorCode) {
        super(message, errorCode, 404, HttpStatus.NOT_FOUND);
    }
}
