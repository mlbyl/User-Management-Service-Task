package com.mlbyl.usermanagementservicetask.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public abstract class BaseException extends RuntimeException {
    private String errorCode;
    private Integer statusCode;
    private HttpStatus status;

    public BaseException(String message, String errorCode, Integer statusCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.status = status;
    }
}
