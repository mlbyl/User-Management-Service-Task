package com.mlbyl.usermanagementservicetask.utils.Result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mlbyl.usermanagementservicetask.exception.BaseException;
import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private String message;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;


    public Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Result(boolean success, T data, String message, String errorCode, Integer statusCode, String path) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.path = path;
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(true, null, message);
    }

    public static <T> Result<?> failure(BaseException exception, String path) {
        return new Result<>(false, null, exception.getMessage(), exception.getErrorCode(),
                exception.getStatusCode(), path);
    }

    public static <T> Result<T> failure(T data, BaseException exception, String path) {
        return new Result<>(false, data, exception.getMessage(), exception.getErrorCode(),
                exception.getStatusCode(), path);
    }


}
