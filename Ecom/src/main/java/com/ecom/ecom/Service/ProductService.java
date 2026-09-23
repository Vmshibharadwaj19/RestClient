package com.ecom.ecom.Service;


import com.ecom.ecom.Dto.ProductCreateRequest;
import com.ecom.ecom.Dto.ProductFilterRequest;
import com.ecom.ecom.Dto.ProductResponseDto;
import com.ecom.ecom.Dto.ProductUpdateRequest;
import com.ecom.ecom.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

        ProductResponseDto createProduct(ProductCreateRequest request);

        ProductResponseDto updateProduct(
                Long id,
                ProductUpdateRequest request
        );

        ProductResponseDto getProductBySku(String sku);

        Page<ProductResponseDto> getAllProducts(ProductFilterRequest filter,
                                                Pageable pageable
        );
        ProductResponseDto getProductById(Long id);
        void changeProductStatus(
                Long id,
                ProductStatus status
        );

        void deleteProduct(Long id);
    }
