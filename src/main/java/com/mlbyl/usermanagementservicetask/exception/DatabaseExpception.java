package com.mlbyl.usermanagementservicetask.exception;

import com.mlbyl.usermanagementservicetask.utils.constant.ErrorMessage;
import org.springframework.http.HttpStatus;

public class DatabaseExpception extends BaseException {
    public DatabaseExpception(String message, String errorCode, Integer statusCode, HttpStatus status) {
        super(extractErrorMessage(message), errorCode, statusCode, status);
    }

    private static String extractErrorMessage(String message) {
        if (message.contains("Detail")) {
            return message.substring(message.indexOf("Detail:"));
        } else if (message.contains("\\")) {
            return message.substring(0, message.indexOf("\\"));
        }
        return ErrorMessage.DATABASE_ERROR_OCCURED.getMessage();
    }
}
