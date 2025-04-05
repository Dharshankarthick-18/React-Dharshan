package com.examly.springapp.services;

import com.examly.springapp.entities.MealPlan;
import com.examly.springapp.repositories.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MealPlanService {
@Autowired
private MealPlanRepository mealPlanRepository;

// Create or Update a MealPlan
public MealPlan saveMealPlan(MealPlan mealPlan) {
return mealPlanRepository.save(mealPlan);
}

// Get a MealPlan by ID
public Optional<MealPlan> getMealPlan(Long id) {
return mealPlanRepository.findById(id);
}

// Delete a MealPlan by ID
public void deleteMealPlan(Long id) {
mealPlanRepository.deleteById(id);
}

// Get all MealPlans
public List<MealPlan> getAllMealPlans() {
return mealPlanRepository.findAll();
}

// Get all MealPlans sorted by a specific field
public List<MealPlan> getAllMealPlansSorted(String field) {
return mealPlanRepository.findAll(Sort.by(Sort.Direction.ASC, field));
}

// Get MealPlans with Pagination
public Page<MealPlan> getMealPlansWithPagination(int page, int size) {
return mealPlanRepository.findAll(PageRequest.of(page, size));
}

// **Update MealPlan**
public Optional<MealPlan> updateMealPlan(Long id, MealPlan updatedMealPlan) {
return mealPlanRepository.findById(id).map(existingMealPlan -> {
existingMealPlan.setDay(updatedMealPlan.getDay());
existingMealPlan.setMealType(updatedMealPlan.getMealType());
existingMealPlan.setUser(updatedMealPlan.getUser());
existingMealPlan.setRecipe(updatedMealPlan.getRecipe());
return mealPlanRepository.save(existingMealPlan);
});
}
}
