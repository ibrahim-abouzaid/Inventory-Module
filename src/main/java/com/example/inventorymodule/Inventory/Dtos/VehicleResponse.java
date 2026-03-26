package com.example.inventorymodule.Inventory.Dtos;

import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VehicleResponse {

    private UUID id;
    private String tenantId;
    private UUID dealerId;
    private String model;
    private BigDecimal price;
    private VehicleStatus status;

}
