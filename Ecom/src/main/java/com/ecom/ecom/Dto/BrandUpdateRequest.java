package com.ecom.ecom.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(title = """
            update request for brand
            """)
    public class BrandUpdateRequest {


        @Size(min=5,max = 100)
        @Schema(example = "Luis vutton")
        private String brandName;

        @Schema(name="brand-samsung")
        private String slug;

        @Schema(example = "https:///")
        private String logoUrl;

        @Schema(example = "https:///")
        private String websiteUrl;
        @Schema(example = "Active")
        public Boolean active;





}
