package com.miniproject.category.dto;

import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Category Request Dto")
public class CategoryRequestDto {

    @NotBlank(message="category name cannot be blank")
    @Size(min=5,max=30,message = "name must be between 5 and 30 characters")
    @Schema(example = "Adventure")
    private String categoryName;

    @NotNull(message="Status cannot be empty")
    @Schema(example = "Active")
    private ActiveStatus activeSw;
}
