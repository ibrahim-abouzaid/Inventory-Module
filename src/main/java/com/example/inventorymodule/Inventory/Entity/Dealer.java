package com.example.inventorymodule.Inventory.Entity;

import com.example.inventorymodule.Inventory.Entity.Enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "dealers",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_dealer_tenant_email",
                columnNames = {"tenant_id", "email"}
        )
)


@EntityListeners(AuditingEntityListener.class)
public class Dealer {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(name = "tenant_id", nullable = false)
        private String tenantId;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Enumerated(EnumType.STRING)
        @Column(name = "subscription_type", nullable = false)
        private SubscriptionType subscriptionType;

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;


}
