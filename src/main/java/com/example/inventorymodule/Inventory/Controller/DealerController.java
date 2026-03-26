package com.example.inventorymodule.Inventory.Controller;

import com.example.inventorymodule.Inventory.Dtos.DealerRequest;
import com.example.inventorymodule.Inventory.Dtos.DealerResponse;
import com.example.inventorymodule.Inventory.Entity.Dealer;
import com.example.inventorymodule.Inventory.Mapper.DealerMapper;
import com.example.inventorymodule.Inventory.Service.DealerService;
import jakarta.persistence.ManyToMany;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dealers")
public class DealerController {
    private  DealerService dealerService;
    private  DealerMapper dealerMapper;

    public DealerController(DealerService dealerService, DealerMapper dealerMapper) {
        this.dealerService = dealerService;
        this.dealerMapper = dealerMapper;
    }

    @PostMapping
    public ResponseEntity<DealerResponse> createDealer(@Valid @RequestBody DealerRequest dealerRequest) {
        Dealer createdDealer = dealerService.createDealer(dealerMapper.toEntity(dealerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(dealerMapper.toDto(createdDealer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponse> getDealerById(@PathVariable UUID id) {
        return ResponseEntity.ok(dealerMapper.toDto(dealerService.getDealerSafely(id)));
    }

    @GetMapping
    public ResponseEntity<List<DealerResponse>> getAllDealers(Pageable pageable) {
        // Spring automatically maps ?page=0&size=10&sort=name,asc to the Pageable object
        return ResponseEntity.ok(dealerService.getAllDealers(pageable).map(dealerMapper::toDto).getContent());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DealerResponse> updateDealer(@PathVariable UUID id,@Valid @RequestBody DealerRequest updateRequest) {
        return ResponseEntity.ok(dealerMapper.toDto(dealerService.updateDealer(id,dealerMapper.toEntity(updateRequest))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable UUID id) {
        dealerService.deleteDealer(id);
        return ResponseEntity.noContent().build();
    }


}
