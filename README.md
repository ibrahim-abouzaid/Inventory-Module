# Inventory Management Module

A multi-tenant backend module built with Spring Boot and PostgreSQL, designed using Clean Architecture principles. This module manages vehicle inventories for multiple dealerships, ensuring strict data isolation between tenants.

## Features
* **Multi-Tenancy:** Strict tenant isolation using `X-Tenant-Id` HTTP headers and ThreadLocal context.
* **Dynamic Filtering:** Advanced search capabilities for vehicles using Spring Data JPA Specifications (filter by model, status, and price range).
* **Clean Architecture:** Clear separation of concerns between Controllers, Services, Repositories, and Domain Entities.
* **Robust Error Handling:** Global exception handling for missing headers, cross-tenant access violations, and malformed requests.
* **DTO Mapping:** Automated object mapping using MapStruct to separate database entities from API contracts.

## Tech Stack
* **Java 17+**
* **Spring Boot 3.x** (Web, Data JPA, Validation)
* **PostgreSQL**
* **Lombok & MapStruct**


## Setup postgresql using docker

```
docker run --name inventory-postgres \
-e POSTGRES_USER=inventory_user \
-e POSTGRES_PASSWORD=inventory_pass \
-e POSTGRES_DB=inventory_db \
-p 5432:5432 \
-d postgres 
```
## API Reference

### Headers Required
`X-Tenant-Id`: Required for all endpoints (except Admin) to identify the current tenant.

### Dealers (`/dealers`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/dealers` | Create a new dealer |
| `GET` | `/dealers/{id}` | Get a specific dealer |
| `GET` | `/dealers` | Get all dealers (Supports pagination) |
| `PATCH` | `/dealers/{id}` | Update a dealer (Partial update) |
| `DELETE` | `/dealers/{id}` | Delete a dealer |

### Vehicles (`/vehicles`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/vehicles?dealerId={id}`| Create a new vehicle for a dealer |
| `GET` | `/vehicles/{id}` | Get a specific vehicle |
| `GET` | `/vehicles` | Search vehicles (Filters: `model`, `status`, `priceMin`, `priceMax`, `subscription=PREMIUM`) |
| `PATCH` | `/vehicles/{id}` | Update a vehicle |
| `DELETE` | `/vehicles/{id}` | Delete a vehicle |

### Admin (`/admin/dealers`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/countBySubscription` | Get global counts of dealers by subscription type |
