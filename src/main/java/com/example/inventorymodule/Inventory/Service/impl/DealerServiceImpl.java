package com.example.inventorymodule.Inventory.Service.impl;

import com.example.inventorymodule.Inventory.Entity.Dealer;
import com.example.inventorymodule.Inventory.Repository.DealerRepository;
import com.example.inventorymodule.Inventory.Service.DealerService;
import com.example.inventorymodule.Inventory.exceptions.CrossTenantAccessException;
import com.example.inventorymodule.Inventory.exceptions.ResourceNotFoundException;
import com.example.inventorymodule.shared.TenantContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DealerServiceImpl implements DealerService {
    private final DealerRepository dealerRepository;

    public DealerServiceImpl(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    @Override
    public Dealer getDealerSafely(UUID id) {
        String currentTenant = TenantContext.get();
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found with ID: " + id));

        // Requirement: Cross-tenant access blocked → 403
        if (!dealer.getTenantId().equals(currentTenant)) {
            throw new CrossTenantAccessException("You do not have permission to access this dealer.");
        }
        return dealer;
    }

    @Override
    public Page<Dealer> getAllDealers(Pageable pageable) {
        String currentTenant = TenantContext.get();
        return dealerRepository.findAllByTenantId(currentTenant, pageable);

    }

    @Override
    @Transactional
    public Dealer createDealer(Dealer newDealer) {
        String currentTenant = TenantContext.get();

        // Force the tenant ID to be the current context's tenant.
        // This prevents malicious users from passing a different tenant ID in the JSON body.
        newDealer.setTenantId(currentTenant);

        return dealerRepository.save(newDealer);
    }

    @Override
    @Transactional
    public Dealer updateDealer(UUID id, Dealer updateRequest) {
        // 1. Fetch and validate tenant access using safe method
        Dealer existingDealer = getDealerSafely(id);

        // 2. Apply partial updates (PATCH semantics)
        if (updateRequest.getName() != null) {
            existingDealer.setName(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            existingDealer.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getSubscriptionType() != null) {
            existingDealer.setSubscriptionType(updateRequest.getSubscriptionType());
        }

        // 3. Save and return
        return dealerRepository.save(existingDealer);

    }

    @Override
    @Transactional
    public void deleteDealer(UUID id) {

        // Fetch and validate tenant access first.
        // If they don't own it, this throws a 403 before any deletion happens.
        Dealer dealer = getDealerSafely(id);

        dealerRepository.delete(dealer);

    }
}
