package com.example.inventorymodule.Inventory.exceptions;

public class MissingTenantException extends RuntimeException {
    public MissingTenantException(String message) {
        super(message);
    }
}
