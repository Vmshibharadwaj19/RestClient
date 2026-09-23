package com.miniproject.category.dto;

import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "Travel update request")
public class TravelPlanUpdateRequest {

    @Size(min = 5, max = 30,
            message = "Plan name must be between 5 and 30 characters")
    @Schema(example = "manali")
    private String planName;
     @Schema(example = "1")
    private Long categoryId;

     @Schema(example = "very beautiful place")
    private String description;

    @Min(value = 10,
            message = "Minimum budget should be at least 10")
    @Max(value = 500000,
            message = "Minimum budget should not exceed 500000")
    @Schema(example = "100000")
    private BigInteger minimumBudget;

    @Schema(example = "Active")
    private ActiveStatus activeStatus;
}