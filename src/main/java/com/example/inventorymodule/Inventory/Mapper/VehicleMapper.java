package com.example.inventorymodule.Inventory.Mapper;

import com.example.inventorymodule.Inventory.Dtos.VehicleRequest;
import com.example.inventorymodule.Inventory.Dtos.VehicleResponse;
import com.example.inventorymodule.Inventory.Entity.Dealer;
import com.example.inventorymodule.Inventory.Entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "dealer.id",source = "dealerId")
    Vehicle toEntity(VehicleRequest vehicleRequest);
    @Mapping(target = "dealerId",source = "dealer.id")
    VehicleResponse toDto(Vehicle vehicle);

}
