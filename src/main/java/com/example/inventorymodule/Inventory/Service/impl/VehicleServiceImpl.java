package com.example.inventorymodule.Inventory.Service.impl;

import com.example.inventorymodule.Inventory.Entity.Dealer;
import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;
import com.example.inventorymodule.Inventory.Entity.Vehicle;
import com.example.inventorymodule.Inventory.Repository.VehicleRepository;
import com.example.inventorymodule.Inventory.Service.DealerService;
import com.example.inventorymodule.Inventory.Service.VehicleService;
import com.example.inventorymodule.Inventory.Utility.VehicleSpecification;
import com.example.inventorymodule.Inventory.exceptions.CrossTenantAccessException;
import com.example.inventorymodule.Inventory.exceptions.ResourceNotFoundException;
import com.example.inventorymodule.shared.TenantContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class VehicleServiceImpl  implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DealerService dealerService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, DealerService dealerService) {
        this.vehicleRepository = vehicleRepository;
        this.dealerService = dealerService;
    }

    @Override
    public Vehicle getVehicleSafely(UUID id) {
        String currentTenant = TenantContext.get();

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + id));

        // Requirement: Cross-tenant access blocked → 403
        if (!vehicle.getTenantId().equals(currentTenant)) {
            throw new CrossTenantAccessException("You do not have permission to access this vehicle.");
        }

        return vehicle;

    }

    @Override
    public Page<Vehicle> getVehiclesWithFilters(String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable) {
        String currentTenant = TenantContext.get();

        // Build the dynamic query
        Specification<Vehicle> spec = VehicleSpecification.withFilters(currentTenant, model, status, priceMin, priceMax);

        return vehicleRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Vehicle> getPremiumVehicles(Pageable pageable) {
        String currentTenant = TenantContext.get();
        return vehicleRepository.findPremiumVehiclesByTenant(currentTenant, pageable);
    }

    @Override
    @Transactional
    public Vehicle createVehicle(UUID dealerId, Vehicle newVehicle) {
        String currentTenant = TenantContext.get();

        // 1. Verify the dealer exists AND belongs to the current tenant.
        // If the dealer belongs to someone else, this method throws a 403 automatically!
        Dealer dealer = dealerService.getDealerSafely(dealerId);

        // 2. Bind relationships and tenant ID
        newVehicle.setTenantId(currentTenant);
        newVehicle.setDealer(dealer);

        // Default status if not provided
        if (newVehicle.getStatus() == null) {
            newVehicle.setStatus(VehicleStatus.AVAILABLE);
        }

        return vehicleRepository.save(newVehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(UUID id, Vehicle updateRequest) {
        Vehicle existingVehicle = getVehicleSafely(id);

        // next use mapping
        if (updateRequest.getModel() != null) {
            existingVehicle.setModel(updateRequest.getModel());
        }
        if (updateRequest.getPrice() != null) {
            existingVehicle.setPrice(updateRequest.getPrice());
        }
        if (updateRequest.getStatus() != null) {
            existingVehicle.setStatus(updateRequest.getStatus());
        }

        return vehicleRepository.save(existingVehicle);

    }

    @Override
    @Transactional
    public void deleteVehicle(UUID id) {
        Vehicle vehicle = getVehicleSafely(id);
        vehicleRepository.delete(vehicle);
    }
}
