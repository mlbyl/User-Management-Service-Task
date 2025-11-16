package com.mlbyl.usermanagementservicetask.utils.constant;

public enum ErrorMessage {
    NO_USERS_FOUND_IN_DATABASE("No users found in database"),
    USER_NOT_FOUND_WITH_ID("User not found with id: "),
    USER_NOT_FOUND_WITH_EMAIL("User not found with email: "),

    EMAIL_ALREADY_TAKEN("Email already taken"),

    VALIDATION_ERROR_OCCURED("Validation error occured"),
    INTERNAL_SERVER_ERROR_OCCURED("Internal server error occured"),
    DATABASE_ERROR_OCCURED("Database error occured");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object arg) {
        return getMessage() + arg;
    }


}
