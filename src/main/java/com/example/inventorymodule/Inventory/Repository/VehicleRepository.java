package com.example.inventorymodule.Inventory.Repository;

import com.example.inventorymodule.Inventory.Entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> , JpaSpecificationExecutor<Vehicle> {

    Optional<Vehicle> findByIdAndTenantId(UUID id, String tenantId);

    // Filter by PREMIUM subscription for the current tenant
    @Query("SELECT v FROM Vehicle v JOIN v.dealer d WHERE v.tenantId = :tenantId AND d.subscriptionType = 'PREMIUM'")
    Page<Vehicle> findPremiumVehiclesByTenant(@Param("tenantId") String tenantId, Pageable pageable);
}
