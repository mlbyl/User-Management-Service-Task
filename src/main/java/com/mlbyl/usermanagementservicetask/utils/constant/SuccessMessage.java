package com.mlbyl.usermanagementservicetask.utils.constant;

public enum SuccessMessage {
    USER_CREATED_SUCCESSFULLY("User created successfully"),
    USER_RETRIEVED_SUCCESSFULLY("User retrieved successfully"),
    ALL_USERS_RETRIEVED_SUCCESSFULLY("All users retrieved successfully"),
    USER_UPDATED_SUCCESSFULLY("User updated successfully"),
    USER_DELETED_SUCCESSFULLY("User deleted successfull");

    private String messsage;

    SuccessMessage(String messsage) {
        this.messsage = messsage;
    }

    public String getMessage() {
        return messsage;
    }

}
