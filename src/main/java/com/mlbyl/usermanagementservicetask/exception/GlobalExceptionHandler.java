package com.mlbyl.usermanagementservicetask.exception;

import com.mlbyl.usermanagementservicetask.utils.Result.Result;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorCode;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<?>> handleBaseException(BaseException baseException, WebRequest request) {
        log.error("Base exception occured: Type: {}, Message: {}, Path: {}, Status: {}", baseException.getClass().getSimpleName(),
                baseException.getMessage(), extractPathPayload(request), baseException.getStatus());

        return ResponseEntity.status(baseException.getStatus())
                .body(Result.failure(baseException, extractPathPayload(request)));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Result<?>> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception, WebRequest request) {


        BaseException ex =
                new DatabaseExpception(exception.getMostSpecificCause().getMessage(), ErrorCode.DATABASE.name(),
                        409,
                        HttpStatus.CONFLICT);

        log.error("Database exception occured: Tyep: {}, Message: {}", ex.getClass().getSimpleName(), ex.getMessage(),
                extractPathPayload(request), ex.getStatus());


        return ResponseEntity.status(ex.getStatus()).body(Result.failure(ex, extractPathPayload(request)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidationException(
            MethodArgumentNotValidException exception, WebRequest request
    ) {

        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        BaseException ex = new ValidationException(ErrorMessage.VALIDATION_ERROR_OCCURED.getMessage(),
                ErrorCode.VALIDATION.name(), HttpStatus.BAD_REQUEST);

        log.error("Validation exception occured: Type: {}, Message: {}, Path: {}, Status:{}", ex.getClass().getSimpleName(), ex.getMessage(), errors,
                extractPathPayload(request), ex.getStatus());

        return ResponseEntity.status(ex.getStatus())
                .body(Result.failure(errors, ex, extractPathPayload(request)));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleRuntimeExpception(Exception exception, WebRequest request) {
        BaseException ex =
                new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR_OCCURED.getMessage(),
                        ErrorCode.SERVER.name(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("Unexpected error occured: Type: {}, Message: {}, Path: {}, Status: {}", ex.getClass().getSimpleName(), ex.getMessage(),
                extractPathPayload(request), ex.getStatus());

        return ResponseEntity.status(ex.getStatus()).body(Result.failure(ex, extractPathPayload(request)));

    }

    private String extractPathPayload(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

}
