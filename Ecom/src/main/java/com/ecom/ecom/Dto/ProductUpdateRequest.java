package com.ecom.ecom.Dto;

import com.ecom.ecom.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = """
         ProductUpdateRequest
        """)
public class ProductUpdateRequest {

    @Size(min = 3, max = 150)
    @Schema(example = "s24 ultra")
    private String name;
    @Schema(example = "very good mobile")
    private String description;

    @DecimalMin("0.01")
    @Schema(example = "1000000")
    private BigDecimal price;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Schema(example = "1000000")
    private BigDecimal discountPercentage;

    @Schema(example = "1")
    private Long categoryId;

    @Schema(example = "1")
    private Long brandId;

    @Schema(example = "hhtps:/")
    private String thumbnailUrl;
    @Schema(example = "false")
    private Boolean featured;

    @Schema(example = "Active")
    private ProductStatus status;

    @NotNull
    Long version;
}
