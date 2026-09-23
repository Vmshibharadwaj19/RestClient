package com.ecom.ecom.Dto;

import com.ecom.ecom.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductFilterRequest {

    private List<Long> brandIds;

    private Long categoryId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal minRating;

    private Boolean featured;

    private ProductStatus status;

    private String search;
}