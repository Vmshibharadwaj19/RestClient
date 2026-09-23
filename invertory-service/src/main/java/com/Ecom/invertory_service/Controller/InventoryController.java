package com.Ecom.invertory_service.Controller;

import com.Ecom.invertory_service.Dto.InventoryCreateRequest;
import com.Ecom.invertory_service.Dto.InventoryResponse;
import com.Ecom.invertory_service.Service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(
        name = "Inventory Management",
        description = "APIs for managing product inventory"
)
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @Operation(summary = "Create inventory")
    public ResponseEntity<InventoryResponse> createInventory(
            @RequestBody InventoryCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(request));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory by product ID")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByProductId(productId)
        );
    }

    @PutMapping("/product/{productId}/add-stock")
    @Operation(summary = "Add stock")
    public ResponseEntity<InventoryResponse> addStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.addStock(productId, quantity)
        );
    }

    @PutMapping("/product/{productId}/reorder-level")
    @Operation(summary = "Update reorder level")
    public ResponseEntity<InventoryResponse> updateReorderLevel(
            @PathVariable Long productId,
            @RequestParam Integer reorderLevel) {

        return ResponseEntity.ok(
                inventoryService.updateReorderLevel(productId, reorderLevel)
        );
    }

    @PutMapping("/product/{productId}/reserve")
    @Operation(summary = "Reserve stock")
    public ResponseEntity<InventoryResponse> reserveStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.reserveStock(productId, quantity)
        );
    }

    @PutMapping("/product/{productId}/release")
    @Operation(summary = "Release reserved stock")
    public ResponseEntity<InventoryResponse> releaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.releaseStock(productId, quantity)
        );
    }

    @PutMapping("/product/{productId}/confirm")
    @Operation(summary = "Confirm reserved stock")
    public ResponseEntity<InventoryResponse> confirmStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                inventoryService.confirmStock(productId, quantity)
        );
    }
}