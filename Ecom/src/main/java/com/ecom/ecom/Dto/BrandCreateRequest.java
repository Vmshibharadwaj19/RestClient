package com.ecom.ecom.Dto;

import com.ecom.ecom.Entities.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BrandCreateRequest {

    @NotBlank(message="brandName cannot be left blank")
    @Size(min=5,max = 100)
    private String brandName;


    private String slug;

    private String logoUrl;

    private String websiteUrl;

    public Boolean active;



}
