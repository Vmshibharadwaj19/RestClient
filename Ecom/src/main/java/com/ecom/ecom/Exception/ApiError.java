package com.ecom.ecom.Exception;


import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(

        int status,

        String error,

        String message,

        LocalDateTime timestamp,

        Map<String, String> fieldErrors

) {

    public ApiError(
            int status,
            String error,
            String message,
            LocalDateTime timestamp
    ) {
        this(
                status,
                error,
                message,
                timestamp,
                null
        );
    }
}