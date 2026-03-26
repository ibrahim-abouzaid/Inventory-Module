package com.example.inventorymodule.Inventory.Controller;

import com.example.inventorymodule.Inventory.Dtos.VehicleRequest;
import com.example.inventorymodule.Inventory.Dtos.VehicleResponse;
import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;
import com.example.inventorymodule.Inventory.Entity.Vehicle;
import com.example.inventorymodule.Inventory.Mapper.VehicleMapper;
import com.example.inventorymodule.Inventory.Service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }



    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @RequestParam UUID dealerId, // We ask for the dealerId as a query param or you could use a DTO
            @RequestBody VehicleRequest vehicleRequest) {

        Vehicle createdVehicle = vehicleService.createVehicle(dealerId,vehicleMapper.toEntity(vehicleRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleMapper.toDto(createdVehicle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.getVehicleSafely(id)));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> searchVehicles(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) String subscription,
            Pageable pageable) {

        // Handle the specific business requirement: GET /vehicles?subscription=PREMIUM
        if ("PREMIUM".equalsIgnoreCase(subscription)) {
            return ResponseEntity.ok(vehicleService.getPremiumVehicles(pageable).map(vehicleMapper::toDto).getContent());
        }

        // Otherwise, fall back to our dynamic specification filters
        return ResponseEntity.ok(vehicleService.getVehiclesWithFilters(model, status, priceMin, priceMax, pageable).map(vehicleMapper::toDto).getContent() );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable UUID id, @RequestBody VehicleRequest updateRequest) {
        return ResponseEntity.ok(vehicleMapper.toDto(vehicleService.updateVehicle(id, vehicleMapper.toEntity(updateRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
