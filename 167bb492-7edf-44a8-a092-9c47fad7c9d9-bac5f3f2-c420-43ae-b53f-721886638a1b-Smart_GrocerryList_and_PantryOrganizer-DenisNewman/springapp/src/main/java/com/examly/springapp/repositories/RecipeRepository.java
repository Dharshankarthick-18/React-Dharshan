package com.examly.springapp.repositories;

import com.examly.springapp.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findRecipesByName(String name);
    List<Recipe> findByNameContainingIgnoreCase(String name);
    List<Recipe> findByNameContainingIgnoreCaseOrderByNameAsc(String name);
     // Pagination: Returns a page of recipes
         Page<Recipe> findAll(Pageable pageable);

             // Pagination + Filtering: Get paginated & filtered recipes by name
                 Page<Recipe> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
