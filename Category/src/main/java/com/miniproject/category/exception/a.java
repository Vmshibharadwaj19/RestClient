package com.miniproject.category.exception;


public class BrandInUseException extends RuntimeException {

    public BrandInUseException(String message) {
        super(message);
    }
}