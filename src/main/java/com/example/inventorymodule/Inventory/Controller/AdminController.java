package com.example.inventorymodule.Inventory.Controller;

import com.example.inventorymodule.Inventory.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/dealers")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

   // @PreAuthorize("hasRole('GLOBAL_ADMIN')") // Enforces strict Role-Based Access Control
    @GetMapping("/countBySubscription")
    public ResponseEntity<Map<String, Long>> getDealerCounts() {

        Map<String, Long> counts = adminService.getGlobalDealerCounts();

        return ResponseEntity.ok(counts);
    }
}
