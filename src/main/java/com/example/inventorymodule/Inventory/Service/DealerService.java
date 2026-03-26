package com.example.inventorymodule.Inventory.Service;

import com.example.inventorymodule.Inventory.Entity.Dealer;
import com.example.inventorymodule.Inventory.Repository.DealerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DealerService {
    Dealer getDealerSafely(UUID id);
    Page<Dealer> getAllDealers(Pageable pageable);
    Dealer createDealer(Dealer newDealer);
    Dealer updateDealer(UUID id, Dealer updateRequest);
    void deleteDealer(UUID id);

}
