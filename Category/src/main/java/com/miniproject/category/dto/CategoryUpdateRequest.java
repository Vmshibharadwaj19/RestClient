package com.miniproject.category.dto;



import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Update request tranfer object")
public class CategoryUpdateRequest {

    @Size(
            min = 5,
            max = 30,
            message = "Category name should be between 5 and 30 characters"
    )
    @Schema(example = "Trekk")
    private String categoryName;

    @Schema(example = "Active")
    private ActiveStatus activeSw;
}