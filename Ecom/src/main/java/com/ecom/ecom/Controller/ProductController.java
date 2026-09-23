package com.ecom.ecom.Controller;

import com.ecom.ecom.Dto.ProductCreateRequest;
import com.ecom.ecom.Dto.ProductFilterRequest;
import com.ecom.ecom.Dto.ProductResponseDto;
import com.ecom.ecom.Dto.ProductUpdateRequest;
import com.ecom.ecom.Exception.ApiError;
import com.ecom.ecom.ProductStatus;
import com.ecom.ecom.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(
        name = "Product Controller",
        description = "APIs for Product management and dynamic filtering"
)
public class ProductController {

    private final ProductService productService;


    // ----------------------------------------------------
    // CREATE PRODUCT
    // ----------------------------------------------------

    @Operation(
            summary = "Create product",
            description = "Creates a new product associated with an active Brand and Category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Brand or Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "SKU already exists or related resource conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        productService.createProduct(request)
                );
    }


    // ----------------------------------------------------
    // UPDATE PRODUCT
    // ----------------------------------------------------

    @Operation(
            summary = "Update product",
            description = "Partially updates an existing product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid update request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product, Brand or Category not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Update conflict",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        request
                )
        );
    }


    // ----------------------------------------------------
    // GET PRODUCT BY SKU
    // ----------------------------------------------------

    @Operation(
            summary = "Get product by SKU",
            description = "Fetches a product using its unique SKU"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ProductResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @GetMapping("/bysku")
    public ResponseEntity<ProductResponseDto> getProductBySku(
            @RequestParam String sku) {

        return ResponseEntity.ok(
                productService.getProductBySku(sku)
        );
    }


    // ----------------------------------------------------
    // GET / FILTER PRODUCTS
    // ----------------------------------------------------

    @Operation(
            summary = "Get and filter products",
            description = """
                    Returns paginated products.

                    All filters are optional.

                    Supports Brand, Category, price range,
                    minimum rating, featured flag,
                    Product status and product-name search.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid filter or pagination parameter",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(

            @ParameterObject
            @ModelAttribute
            ProductFilterRequest filter,

            @ParameterObject
            Pageable pageable) {

        return ResponseEntity.ok(
                productService.getAllProducts(
                        filter,
                        pageable
                )
        );
    }


    // ----------------------------------------------------
    // CHANGE PRODUCT STATUS
    // ----------------------------------------------------

    @Operation(
            summary = "Change product status",
            description = """
                    Changes product status.

                    Example statuses:
                    ACTIVE
                    INACTIVE
                    OUT_OF_STOCK
                    DISCONTINUED
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product status changed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeProductStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus status) {

        productService.changeProductStatus(
                id,
                status
        );

        return ResponseEntity
                .noContent()
                .build();
    }


    // ----------------------------------------------------
    // DELETE PRODUCT
    // ----------------------------------------------------

    @Operation(
            summary = "Delete product",
            description = "Deletes the specified product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product cannot be deleted due to existing references",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ApiError.class
                            )
                    )
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/productId/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.getProductById(id));
    }
}