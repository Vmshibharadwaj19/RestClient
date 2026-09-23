package com.miniproject.category.dto;

import com.miniproject.category.ActiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Travel request dto")
public class TravelRequestDto {
    @NotBlank(message = "planName cannot be blank")
    @Size(min=5,max=30,message = "plan name should be in between 5 and 30 characters")
    private String planName;

    @NotNull(message="this field cannot be null")
     private Long categoryId;

    private String description;
     @NotNull(message="Budget cannot be blank")
     @Min(value = 10, message = "Minimum budget should be at least 10")
     @Max(value = 500000, message = "Minimum budget should not exceed 500000")
     @Schema(example = "10000")
     private BigInteger minimumBudget;

     @NotNull(message = "status cannot be empty")
     @Schema(example = "Active")
    private ActiveStatus activeStatus;
}
