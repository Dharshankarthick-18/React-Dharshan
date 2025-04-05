    package com.examly.springapp.services;

    import com.examly.springapp.entities.Recipe;
    import com.examly.springapp.repositories.RecipeRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // Get all recipes
    public List<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
    }

    // Get a single recipe by ID
    public Optional<Recipe> getRecipeById(Long id) {
    return recipeRepository.findById(id);
    }

    // Create or update a recipe
    public Recipe saveRecipe(Recipe recipe) {
    return recipeRepository.save(recipe);
    }

    // Delete a recipe by ID
    public void deleteRecipe(Long id) {
    recipeRepository.deleteById(id);
    }

    // Search recipes by name (case-insensitive)
    public List<Recipe> findRecipesByName(String name) {
    return recipeRepository.findByNameContainingIgnoreCase(name);
    }

    // Get paginated recipes
    public Page<Recipe> getRecipesWithPagination(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return recipeRepository.findAll(pageable);
    }

    // Get paginated and sorted recipes
    public Page<Recipe> getRecipesWithPaginationAndSorting(int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    return recipeRepository.findAll(pageable);
    }

    // Update an existing recipe
    public Optional<Recipe> updateRecipe(Long id, Recipe updatedRecipe) {
    return recipeRepository.findById(id).map(existingRecipe -> {
    existingRecipe.setName(updatedRecipe.getName());
    existingRecipe.setInstructions(updatedRecipe.getInstructions());
    return recipeRepository.save(existingRecipe);
    });
    }
    }
