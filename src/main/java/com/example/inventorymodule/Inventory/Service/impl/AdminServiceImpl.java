package com.example.inventorymodule.Inventory.Service.impl;

import com.example.inventorymodule.Inventory.Repository.DealerRepository;
import com.example.inventorymodule.Inventory.Service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final DealerRepository dealerRepository;

    public AdminServiceImpl(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    @Override
    public Map<String, Long> getGlobalDealerCounts() {
        List<Object[]> results = dealerRepository.countBySubscriptionTypeGlobally();

        // Convert the raw SQL result into a clean Java Map
        return results.stream().collect(Collectors.toMap(
                row -> row[0].toString(), // The subscription type (e.g., "PREMIUM")
                row -> (Long) row[1]      // The count (e.g., 42)
        ));    }
}
