package com.Ecom.invertory_service.Dto;

import lombok.AllArgsConstructor;


public record InventoryResponse(
        Long id,
        Long productId,
        Integer availableQuantity,
        Integer reservedQuantity,
        Integer reOrderLevel,
        Long version
) {

}
