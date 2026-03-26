package com.example.inventorymodule.Inventory.Dtos;

import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VehicleRequest {


    private UUID id;

    @NotBlank(message = "Tenant Id is required")
    private String tenantId;

    @NotNull(message = "dealerId is required")
    private UUID dealerId;

    @NotBlank(message = "model is required")
    private String model;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal price;

    @NotNull(message = "Status is required")
    @Pattern(regexp = "^(SOLD|AVAILABLE)$", message = "Vehicle Status  must be either AVAILABLE or SOLD")
    private VehicleStatus status;

}
