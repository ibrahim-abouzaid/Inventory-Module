package com.example.inventorymodule.Inventory.Service;

import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;
import com.example.inventorymodule.Inventory.Entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface VehicleService {
    Vehicle getVehicleSafely(UUID id);
    Page<Vehicle> getVehiclesWithFilters(String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable);
    Page<Vehicle> getPremiumVehicles(Pageable pageable);
    Vehicle createVehicle(UUID dealerId, Vehicle newVehicle);
    Vehicle updateVehicle(UUID id, Vehicle updateRequest);
    void deleteVehicle(UUID id);
}
