package com.example.inventorymodule.Inventory.exceptions;

public class CrossTenantAccessException extends RuntimeException {
    public CrossTenantAccessException(String message) {
        super(message);
    }
}
