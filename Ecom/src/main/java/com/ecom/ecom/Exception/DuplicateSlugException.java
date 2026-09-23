package com.ecom.ecom.Exception;

public class DuplicateSlugException extends RuntimeException{
    public DuplicateSlugException(String message){
        super(message);
    }
}
