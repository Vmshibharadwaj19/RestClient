package com.Ecom.invertory_service.Service;

import com.Ecom.invertory_service.Dto.InventoryCreateRequest;
import com.Ecom.invertory_service.Dto.InventoryResponse;
import com.Ecom.invertory_service.Entitites.Inventory;

public interface InventoryService {

    InventoryResponse createInventory(
            InventoryCreateRequest request
    );

    InventoryResponse getInventoryByProductId(
            Long productId
    );

    InventoryResponse addStock(
            Long productId,
            Integer quantity
    );

    InventoryResponse updateReorderLevel(
            Long productId,
            Integer reorderLevel
    );

    InventoryResponse reserveStock(
            Long productId,
            Integer quantity
    );

    InventoryResponse releaseStock(
            Long productId,
            Integer quantity
    );

    InventoryResponse confirmStock(
            Long productId,
            Integer quantity
    );
}