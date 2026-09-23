package com.ecom.ecom.Exception;

public class BrandInUseException extends RuntimeException {

    public BrandInUseException(String message) {
        super(message);
    }
}