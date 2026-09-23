package com.miniproject.category.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "standard api error")
public class ApiError {

    @Schema(example = "404")
    Integer code;
    @Schema(example = "Not found")
    String message;
    @Schema(example = "Cause")
    String description;

}
