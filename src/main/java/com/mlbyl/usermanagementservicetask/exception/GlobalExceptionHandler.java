package com.mlbyl.usermanagementservicetask.exception;

import com.mlbyl.usermanagementservicetask.utils.Result.Result;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorCode;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorMessage;
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
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<?>> handleBaseException(BaseException baseException, WebRequest request) {
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

        return ResponseEntity.status(ex.getStatus())
                .body(Result.failure(errors, ex, extractPathPayload(request)));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleRuntimeExpception(Exception exception, WebRequest request) {
        BaseException ex =
                new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR_OCCURED.getMessage(),
                        ErrorCode.SERVER.name(), HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(ex.getStatus()).body(Result.failure(ex, extractPathPayload(request)));

    }

    private String extractPathPayload(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

}
