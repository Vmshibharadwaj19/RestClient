package com.Ecom.invertory_service.Repository;

import com.Ecom.invertory_service.Entitites.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    boolean existsByProductId(Long productId);

    Optional<Inventory> findByProductId(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
           select i
           from Inventory i
           where i.productId = :productId
           """)
    Optional<Inventory> findByProductIdForUpdate(
            @Param("productId") Long productId
    );
}