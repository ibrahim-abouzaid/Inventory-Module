package com.example.inventorymodule.Inventory.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;
@Data
public class DealerResponse {

    private UUID id;
    private String tenantId;
    private String name;
    private String email;
    private String subscriptionType;

}
