package com.examly.springapp.repositories;

import com.examly.springapp.entities.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserId(Long userId); // Get meal plans for a specific user
        
            List<MealPlan> findByDay(String day); // Get meal plans for a specific day
            }
            