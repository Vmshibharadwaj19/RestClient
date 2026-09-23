package com.miniproject.category.controller;

import com.miniproject.category.dto.*;
import com.miniproject.category.service.TravelPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travelplan")
@RequiredArgsConstructor
public class TravelController {
    private final TravelPlanService travelPlanService;

    @GetMapping
    public ResponseEntity<Page<TravelResponseDto>> findAll(Pageable pageable) {

            return ResponseEntity.ok().body(travelPlanService.findAll(pageable));



    }

    @PostMapping("/create")
    public ResponseEntity<TravelResponseDto> create(@Valid @RequestBody TravelRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(travelPlanService.create(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TravelResponseDto> update(@PathVariable Long id,@Valid @RequestBody TravelPlanUpdateRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(travelPlanService.update(id,request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        travelPlanService.deleteTravelPlan(id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
