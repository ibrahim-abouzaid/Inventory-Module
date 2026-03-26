package com.example.inventorymodule.Inventory.Utility;

import com.example.inventorymodule.Inventory.Entity.Enums.VehicleStatus;
import com.example.inventorymodule.Inventory.Entity.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class VehicleSpecification {
    public static Specification<Vehicle> withFilters(
            String tenantId, String model, VehicleStatus status, BigDecimal priceMin, BigDecimal priceMax) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. MANDATORY: Always restrict the query to the current tenant
            predicates.add(cb.equal(root.get("tenantId"), tenantId));

            // 2. Optional Filters
            if (model != null && !model.trim().isEmpty()) {
                // Case-insensitive partial match (LIKE %model%)
                predicates.add(cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%"));
            }

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (priceMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceMin));
            }

            if (priceMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
