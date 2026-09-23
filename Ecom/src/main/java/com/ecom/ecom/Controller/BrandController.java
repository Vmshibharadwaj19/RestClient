package com.ecom.ecom.Controller;

import com.ecom.ecom.Dto.BrandCreateRequest;
import com.ecom.ecom.Dto.BrandResponseDto;
import com.ecom.ecom.Dto.BrandUpdateRequest;
import com.ecom.ecom.Exception.ApiError;
import com.ecom.ecom.Repository.BrandRepo;
import com.ecom.ecom.Service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@Tag(name = "Brand Controller",
description = "Controller endpoints for Brand Functionalities")
public class BrandController {

    private final BrandService brandRepo;

    @Operation(
            summary = """
                    creating Brand Record
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "created",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = BrandResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No resource found",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            )
    })

    @PostMapping("/create")
    public ResponseEntity<BrandResponseDto> create(@Valid @RequestBody BrandCreateRequest request)
    {
      return  ResponseEntity.status(HttpStatus.CREATED).body(brandRepo.createBrand(request));
    }
    @Operation(
            summary = """
                    creating Brand Record
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Update",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = BrandResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No resource found",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            )
    })

    @PatchMapping("/{id}")
    public ResponseEntity<BrandResponseDto> update(@PathVariable Long id, @Valid @RequestBody BrandUpdateRequest request)
    {
        return  ResponseEntity.ok(brandRepo.updateBrand(id, request));
    }
    @Operation(
            summary = """
                    creating Brand Record
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetch all brands",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = BrandResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No resource found",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            )
    })

    @GetMapping
    public ResponseEntity<Page<BrandResponseDto>> getAllBrands(Pageable pageable)
    {
        return ResponseEntity.ok().body(brandRepo.getAllBrands(pageable));
    }
    @Operation(
            summary = """
                    creating Brand Record
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation =BrandResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "fetch all brands by slug",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No resource found",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            )
    })

    @GetMapping("/byslug")
    public ResponseEntity<BrandResponseDto> getBySlug( @RequestParam String slug)
    {
        return ResponseEntity.ok().body(brandRepo.getBrandBySlug(slug));
    }
    @Operation(
            summary = """
                    creating Brand Record
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "209",
                    description = "deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = BrandResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No resource found",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema =  @Schema(implementation = ApiError.class)
                    )
            )
    })

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        brandRepo.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Change brand status",
            description = "Activates or deactivates an existing brand"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Brand status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid active status"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Brand not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        brandRepo.changeBrandStatus(id, active);

        return ResponseEntity.noContent().build();
    }

}
