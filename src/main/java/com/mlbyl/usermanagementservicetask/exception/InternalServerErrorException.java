package com.mlbyl.usermanagementservicetask.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, 500, status);
    }
}
