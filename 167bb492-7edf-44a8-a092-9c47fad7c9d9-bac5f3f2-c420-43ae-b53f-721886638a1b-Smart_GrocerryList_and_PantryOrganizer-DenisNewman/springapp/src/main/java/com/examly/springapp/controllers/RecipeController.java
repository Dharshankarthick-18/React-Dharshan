package com.examly.springapp.controllers;

import com.examly.springapp.entities.Recipe;
import com.examly.springapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

@Autowired
private RecipeService recipeService;

// Get all recipes
@GetMapping
public List<Recipe> getAllRecipes() {
return recipeService.getAllRecipes();
}

// Get recipe by ID
@GetMapping("/{id}")
public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
return recipeService.getRecipeById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}

// Search recipes by name (case-insensitive)
@GetMapping("/search")
public ResponseEntity<List<Recipe>> findRecipesByName(@RequestParam String name) {
List<Recipe> recipes = recipeService.findRecipesByName(name);
return recipes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(recipes);
}

// Create a new recipe
@PostMapping
public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
return new ResponseEntity<>(recipeService.saveRecipe(recipe), HttpStatus.CREATED);
}

// Update an existing recipe
@PutMapping("/{id}")
public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
return recipeService.updateRecipe(id, recipe)
.map(updatedRecipe -> ResponseEntity.ok().body(updatedRecipe))
.orElse(ResponseEntity.notFound().build());
}

// Delete a recipe by ID
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
if (recipeService.getRecipeById(id).isPresent()) {
recipeService.deleteRecipe(id);
return ResponseEntity.ok().build();
}
return ResponseEntity.notFound().build();
}

// Get paginated recipes
@GetMapping("/page")
public ResponseEntity<Page<Recipe>> getRecipesWithPagination(
@RequestParam(defaultValue = "0") int page,
@RequestParam(defaultValue = "5") int size) {
return ResponseEntity.ok(recipeService.getRecipesWithPagination(page, size));
}

// Get paginated and sorted recipes
@GetMapping("/page/sorted")
public ResponseEntity<Page<Recipe>> getRecipesWithPaginationAndSorting(
@RequestParam(defaultValue = "0") int page,
@RequestParam(defaultValue = "5") int size,
@RequestParam(defaultValue = "name") String sortBy) {
return ResponseEntity.ok(recipeService.getRecipesWithPaginationAndSorting(page, size, sortBy));
}
}
