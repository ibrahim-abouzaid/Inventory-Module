package com.example.inventorymodule.Inventory.Repository;

import com.example.inventorymodule.Inventory.Entity.Dealer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, UUID> {
    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);

    // Optional scoped finder
    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

    // Admin query: Count by subscription globally (ignoring tenant)
    @Query("SELECT d.subscriptionType, COUNT(d) FROM Dealer d GROUP BY d.subscriptionType")
    List<Object[]> countBySubscriptionTypeGlobally();
}
