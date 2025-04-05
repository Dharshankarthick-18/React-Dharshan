package com.examly.springapp.controllers;

import com.examly.springapp.entities.MealPlan;
import com.examly.springapp.services.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mealplans")
public class MealPlanController {

@Autowired
private MealPlanService mealPlanService;

// Create a MealPlan
@PostMapping
public MealPlan createMealPlan(@RequestBody MealPlan mealPlan) {
return mealPlanService.saveMealPlan(mealPlan);
}

// Get a MealPlan by ID
@GetMapping("/{id}")
public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long id) {
Optional<MealPlan> mealPlan = mealPlanService.getMealPlan(id);
return mealPlan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}

// Get all MealPlans
@GetMapping
public List<MealPlan> getAllMealPlans() {
return mealPlanService.getAllMealPlans();
}

// Update a MealPlan
@PutMapping("/{id}")
public ResponseEntity<MealPlan> updateMealPlan(@PathVariable Long id, @RequestBody MealPlan updatedMealPlan) {
Optional<MealPlan> updated = mealPlanService.updateMealPlan(id, updatedMealPlan);
return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}

// Delete a MealPlan
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
mealPlanService.deleteMealPlan(id);
return ResponseEntity.noContent().build();
}

// Get MealPlans sorted by a field
@GetMapping("/sorted/{field}")
public List<MealPlan> getSortedMealPlans(@PathVariable String field) {
return mealPlanService.getAllMealPlansSorted(field);
}
}