package com.ecom.ecom.Repository;

import com.ecom.ecom.Entities.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>  {
    boolean existsByCategory_Id(Long id);
    boolean existsByBrandId(Long id);
    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select p from Product p where p .id= :id")
//    Optional<Product> findByIdForUpdate(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {
            "brand","category"
    })
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
