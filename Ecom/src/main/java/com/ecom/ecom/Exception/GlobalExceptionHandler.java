package com.ecom.ecom.Exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // ----------------------------
    // RESOURCE NOT FOUND
    // ----------------------------

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


    // ----------------------------
    // DUPLICATE BRAND NAME
    // ----------------------------

    @ExceptionHandler(DuplicateBrandNameException.class)
    public ResponseEntity<ApiError> handleDuplicateBrandName(
            DuplicateBrandNameException ex) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // ----------------------------
    // DUPLICATE SLUG
    // ----------------------------

    @ExceptionHandler(DuplicateSlugException.class)
    public ResponseEntity<ApiError> handleDuplicateSlug(
            DuplicateSlugException ex) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // ----------------------------
    // BRAND CURRENTLY IN USE
    // ----------------------------

    @ExceptionHandler(BrandInUseException.class)
    public ResponseEntity<ApiError> handleBrandInUse(
            BrandInUseException ex) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // ----------------------------
    // @VALID VALIDATION ERRORS
    // ----------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Request validation failed",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }


    // ----------------------------
    // ILLEGAL ARGUMENT
    // ----------------------------

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex) {

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }


    // ----------------------------
    // DATABASE CONSTRAINT FAILURE
    // ----------------------------

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        log.error("Database integrity violation", ex);

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Data Conflict",
                "The operation violates a database constraint",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    // ----------------------------
    // DATABASE ERROR
    // ----------------------------

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDataAccessException(
            DataAccessException ex) {

        log.error("Database operation failed", ex);

        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database Error",
                "Unable to complete the database operation",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }


    // ----------------------------
    // FALLBACK
    // ----------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(
            Exception ex) {

        log.error("Unexpected application error", ex);

        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ApiError> handleCategoryInUseException(CategoryInUseException ex) {


        log.error("Category In Use", ex);

        ApiError errors=new ApiError(HttpStatus.CONFLICT.value(),
                "There are products associated with the category",
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);

    }
    @ExceptionHandler(DuplicateCategoryNameException.class)
    public ResponseEntity<ApiError> handleDuplicateCategoryNameException(CategoryInUseException ex) {


        log.error("CategorybName exists in the dv", ex);

        ApiError errors=new ApiError(HttpStatus.CONFLICT.value(),
                "Duplicate Category name",
                ex.getMessage(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);

    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", 409);
        response.put("error", "Conflict");
        response.put("message",
                "Product was modified by another user. Please refresh and try again.");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}