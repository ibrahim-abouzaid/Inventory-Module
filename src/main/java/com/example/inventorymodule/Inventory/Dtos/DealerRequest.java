package com.example.inventorymodule.Inventory.Dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class DealerRequest {

    private UUID id;

    @NotBlank(message = "Tenant Id is required")
    private String tenantId;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "enter valid email ")
    private String email;

    @NotBlank(message = "Subscription type is required")
    @Pattern(regexp = "^(BASIC|PREMIUM)$", message = "Subscription type must be either BASIC or PREMIUM")
    private String subscriptionType;


}
