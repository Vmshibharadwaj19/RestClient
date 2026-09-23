package com.Ecom.invertory_service.ServiceImpl;

import com.Ecom.invertory_service.Client.ProductRestClient;
import com.Ecom.invertory_service.Dto.InventoryCreateRequest;
import com.Ecom.invertory_service.Dto.InventoryResponse;
import com.Ecom.invertory_service.Entitites.Inventory;
import com.Ecom.invertory_service.Exception.InsufficientStockException;
import com.Ecom.invertory_service.Exception.InventoryAlreadyExistsException;
import com.Ecom.invertory_service.Exception.InventoryNotFoundException;
import com.Ecom.invertory_service.Exception.InavlidStockException;
import com.Ecom.invertory_service.Repository.InventoryRepo;
import com.Ecom.invertory_service.Service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo repo;
    private final ProductRestClient productClient;


    // =====================================================
    // CREATE INVENTORY
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse createInventory(
            InventoryCreateRequest request) {

        log.debug(
                "Creating inventory for product id: {}",
                request.getProductId()
        );

        // Product belongs to another microservice.
        // Validate that it exists before creating inventory.
        productClient.validateProductIdExists(
                request.getProductId()
        );

        if (repo.existsByProductId(request.getProductId())) {

            throw new InventoryAlreadyExistsException(
                    "Inventory already exists for product id: "
                            + request.getProductId()
            );
        }

        Inventory inventory = new Inventory();

        inventory.setProductId(
                request.getProductId()
        );

        inventory.setAvailableQuantity(
                request.getAvailableQuantity()
        );

        // reservedQuantity stays at entity default = 0

        // Keep entity default if client doesn't provide reorderLevel.
        if (request.getReorderLevel() != null) {
            inventory.setReorderLevel(
                    request.getReorderLevel()
            );
        }

        try {

            Inventory saved = repo.save(inventory);

            log.info(
                    "Inventory created successfully for product id: {}, inventory id: {}",
                    saved.getProductId(),
                    saved.getId()
            );

            return mapToInventoryResponse(saved);

        } catch (DataIntegrityViolationException ex) {

            /*
             * Important:
             *
             * Request A -> exists = false
             * Request B -> exists = false
             *
             * Both could attempt INSERT.
             *
             * The UNIQUE constraint on product_id is the
             * final database-level protection.
             */

            throw new InventoryAlreadyExistsException(
                    "Inventory already exists for product id: "
                            + request.getProductId()
            );
        }
    }


    // =====================================================
    // GET INVENTORY
    // =====================================================

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(
            Long productId) {

        log.debug(
                "Fetching inventory for product id: {}",
                productId
        );

        Inventory inventory =
                getInventoryOrThrow(productId);

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // ADD STOCK
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse addStock(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        /*
         * Use a lock because warehouse stock could be added
         * while another request is reserving stock.
         */
        Inventory inventory =
                getInventoryForUpdateOrThrow(productId);

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        + quantity
        );

        /*
         * No repo.save().
         *
         * inventory is a managed entity inside this transaction.
         * Hibernate dirty checking will generate UPDATE at commit.
         */

        log.info(
                "Added {} units to product id: {}. New available quantity: {}",
                quantity,
                productId,
                inventory.getAvailableQuantity()
        );

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // UPDATE REORDER LEVEL
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse updateReorderLevel(
            Long productId,
            Integer reorderLevel) {

        if (reorderLevel == null || reorderLevel < 0) {

            throw new InavlidStockException(
                    "Reorder level cannot be negative"
            );
        }

        Inventory inventory =
                getInventoryOrThrow(productId);

        inventory.setReorderLevel(reorderLevel);

        log.info(
                "Reorder level updated for product id: {} to {}",
                productId,
                reorderLevel
        );

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // RESERVE STOCK
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse reserveStock(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        /*
         * SELECT ... FOR UPDATE
         *
         * Another transaction trying to modify the same
         * inventory row must wait.
         */
        Inventory inventory =
                getInventoryForUpdateOrThrow(productId);

        if (inventory.getAvailableQuantity() < quantity) {

            log.warn(
                    "Insufficient stock for product id: {}. Available: {}, requested: {}",
                    productId,
                    inventory.getAvailableQuantity(),
                    quantity
            );

            throw new InsufficientStockException(
                    "Insufficient stock for product id: "
                            + productId
            );
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        - quantity
        );

        inventory.setReservedQuantity(
                inventory.getReservedQuantity()
                        + quantity
        );

        log.info(
                "Reserved {} units for product id: {}. Available: {}, reserved: {}",
                quantity,
                productId,
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity()
        );

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // RELEASE RESERVED STOCK
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse releaseStock(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        Inventory inventory =
                getInventoryForUpdateOrThrow(productId);

        if (inventory.getReservedQuantity() < quantity) {

            throw new InavlidStockException(
                    "Cannot release more stock than currently reserved"
            );
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity()
                        - quantity
        );

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity()
                        + quantity
        );

        log.info(
                "Released {} units for product id: {}. Available: {}, reserved: {}",
                quantity,
                productId,
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity()
        );

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // CONFIRM RESERVED STOCK
    // =====================================================

    @Override
    @Transactional
    public InventoryResponse confirmStock(
            Long productId,
            Integer quantity) {

        validatePositiveQuantity(quantity);

        Inventory inventory =
                getInventoryForUpdateOrThrow(productId);

        if (inventory.getReservedQuantity() < quantity) {

            throw new InavlidStockException(
                    "Cannot confirm more stock than currently reserved"
            );
        }

        /*
         * availableQuantity is NOT changed here.
         *
         * It was already reduced during reserveStock().
         */
        inventory.setReservedQuantity(
                inventory.getReservedQuantity()
                        - quantity
        );

        log.info(
                "Confirmed {} reserved units for product id: {}. Remaining reserved: {}",
                quantity,
                productId,
                inventory.getReservedQuantity()
        );

        return mapToInventoryResponse(inventory);
    }


    // =====================================================
    // COMMON LOOKUP
    // =====================================================

    private Inventory getInventoryOrThrow(
            Long productId) {

        return repo.findByProductId(productId)
                .orElseThrow(() -> {

                    log.warn(
                            "Inventory not found for product id: {}",
                            productId
                    );

                    return new InventoryNotFoundException(
                            "Inventory not found for product id: "
                                    + productId
                    );
                });
    }


    // =====================================================
    // LOCKING LOOKUP
    // =====================================================

    private Inventory getInventoryForUpdateOrThrow(
            Long productId) {

        return repo.findByProductIdForUpdate(productId)
                .orElseThrow(() -> {

                    log.warn(
                            "Inventory not found for product id: {}",
                            productId
                    );

                    return new InventoryNotFoundException(
                            "Inventory not found for product id: "
                                    + productId
                    );
                });
    }


    // =====================================================
    // COMMON QUANTITY VALIDATION
    // =====================================================

    private void validatePositiveQuantity(
            Integer quantity) {

        if (quantity == null || quantity <= 0) {

            throw new InavlidStockException(
                    "Quantity must be greater than zero"
            );
        }
    }


    // =====================================================
    // MAPPER
    // =====================================================

    private InventoryResponse mapToInventoryResponse(
            Inventory inventory) {

        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getAvailableQuantity(),
                inventory.getReservedQuantity(),
                inventory.getReorderLevel(),
                inventory.getVersion()
        );
    }
}