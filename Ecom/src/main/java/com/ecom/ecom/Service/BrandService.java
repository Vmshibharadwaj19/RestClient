package com.ecom.ecom.Service;

import com.ecom.ecom.Dto.BrandCreateRequest;
import com.ecom.ecom.Dto.BrandResponseDto;
import com.ecom.ecom.Dto.BrandUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    BrandResponseDto createBrand(BrandCreateRequest request);

    BrandResponseDto updateBrand(Long id, BrandUpdateRequest request);

    BrandResponseDto getBrandById(Long id);

    Page<BrandResponseDto> getAllBrands(Pageable pageable);

    BrandResponseDto getBrandBySlug(String slug);

    void changeBrandStatus(Long id, Boolean active);

    void deleteBrand(Long id);
}
