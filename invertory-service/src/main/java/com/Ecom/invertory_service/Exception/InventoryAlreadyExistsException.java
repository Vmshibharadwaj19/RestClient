package com.Ecom.invertory_service.Exception;

public class InventoryAlreadyExistsException extends RuntimeException{

    public  InventoryAlreadyExistsException(String message) {
        super(message);
    }
}
