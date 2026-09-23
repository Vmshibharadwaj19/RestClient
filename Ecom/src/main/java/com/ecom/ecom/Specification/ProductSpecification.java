package com.ecom.ecom.Specification;

import com.ecom.ecom.Entities.Product;
import com.ecom.ecom.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasBrands(
            List<Long> brandIds) {

        return (root, query, cb) -> {

            if (brandIds == null || brandIds.isEmpty()) {
                return cb.conjunction();
            }

            return root.get("brand")
                    .get("id")
                    .in(brandIds);
        };
    }

    public static Specification<Product> hasCategory(
            Long categoryId) {

        return (root, query, cb) -> {

            if (categoryId == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("category").get("id"),
                    categoryId
            );
        };
    }

    public static Specification<Product> priceGreaterThanOrEqual(
            BigDecimal minPrice) {

        return (root, query, cb) -> {

            if (minPrice == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(
                    root.get("price"),
                    minPrice
            );
        };
    }

    public static Specification<Product> priceLessThanOrEqual(
            BigDecimal maxPrice) {

        return (root, query, cb) -> {

            if (maxPrice == null) {
                return cb.conjunction();
            }

            return cb.lessThanOrEqualTo(
                    root.get("price"),
                    maxPrice
            );
        };
    }

    public static Specification<Product> ratingGreaterThanOrEqual(
            BigDecimal minRating) {

        return (root, query, cb) -> {

            if (minRating == null) {
                return cb.conjunction();
            }

            return cb.greaterThanOrEqualTo(
                    root.get("rating"),
                    minRating
            );
        };
    }

    public static Specification<Product> isFeatured(
            Boolean featured) {

        return (root, query, cb) -> {

            if (featured == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("featured"),
                    featured
            );
        };
    }

    public static Specification<Product> hasStatus(
            ProductStatus status) {

        return (root, query, cb) -> {

            if (status == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("status"),
                    status
            );
        };
    }

    public static Specification<Product> containsName(
            String search) {

        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }

            return cb.like(
                    cb.lower(root.get("productName")),
                    "%" + search.toLowerCase() + "%"
            );
        };
    }
}