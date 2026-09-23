package com.miniproject.category.service;

import com.miniproject.category.dto.TravelPlanUpdateRequest;
import com.miniproject.category.dto.TravelRequestDto;
import com.miniproject.category.dto.TravelResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TravelPlanService {
   public TravelResponseDto create(TravelRequestDto request);

   public TravelResponseDto update(Long id, TravelPlanUpdateRequest request);

   public Page<TravelResponseDto> findAll(Pageable pageable);

   public boolean deleteTravelPlan(Long id);

   public boolean changeTravelStatus(Long id);


}
