package com.example.inventorymodule.shared;

import com.example.inventorymodule.Inventory.exceptions.MissingTenantException;

public final  class TenantContext {
    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    private TenantContext() {}

    public static void set(String tenantId) {
        TENANT.set(tenantId);
    }

    public static String get() {
        return TENANT.get();
    }

    public static String getRequired() {
        String tenantId = TENANT.get();
        if (tenantId == null || tenantId.isBlank()) {
            throw new MissingTenantException("X-Tenant-Id header is required");
        }
        return tenantId;
    }

    public static void clear() {
        TENANT.remove();   // .remove() not .set(null) — avoids memory leak in thread pools
    }
}
