package com.example.inventorymodule.Inventory.Mapper;

import com.example.inventorymodule.Inventory.Dtos.DealerRequest;
import com.example.inventorymodule.Inventory.Dtos.DealerResponse;
import com.example.inventorymodule.Inventory.Entity.Dealer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealerMapper {

    Dealer toEntity (DealerRequest  dealerRequest);
    DealerResponse toDto (Dealer  dealer);
}
