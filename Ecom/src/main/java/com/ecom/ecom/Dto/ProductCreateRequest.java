package com.ecom.ecom.Dto;

import com.ecom.ecom.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = """
           ProductRequestDto
        """)
@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    @Schema(example = "s24 ultra")
    private String name;

    @NotBlank
    @Schema(example = "sk1-121")
    private String sku;

    @Schema(example = "Mobile phone by samsung")
    private String description;

    @NotNull
    @DecimalMin("0.01")
    @Schema(example = "10000")
    private BigDecimal price;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(example = "30")
    private BigDecimal discountPercentage;

    @NotNull
    @Schema(example = "1")
    private Long categoryId;

    @NotNull
    @Schema(example = "1")
    private Long brandId;

    @Schema(example = "hhtps:google/images/uyydrtgyhujk")
    private String thumbnailUrl;

    @Schema(example = "false")
    private Boolean featured;

    @Schema(example = "false")
    private ProductStatus status;
}
