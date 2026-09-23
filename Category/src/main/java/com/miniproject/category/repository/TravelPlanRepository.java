package com.miniproject.category.repository;

import com.miniproject.category.entitites.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlan,Long> {
}
