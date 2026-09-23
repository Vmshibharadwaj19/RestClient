package com.ecom.ecom.Repository;

import com.ecom.ecom.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand,Long> {

    Optional<Brand> findBySlug(String slug);
    public boolean existsBySlug(String slug);
    public  boolean existsByBrandNameIgnoreCase(String brandName);
}
