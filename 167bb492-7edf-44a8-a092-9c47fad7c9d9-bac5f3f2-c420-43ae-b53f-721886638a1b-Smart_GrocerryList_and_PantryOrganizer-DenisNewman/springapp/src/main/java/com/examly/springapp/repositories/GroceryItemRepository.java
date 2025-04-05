package com.examly.springapp.repositories;

import com.examly.springapp.entities.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
   List<GroceryItem> findByCategory(String category);
   List<GroceryItem> findByNameContaining(String keyword);
   List<GroceryItem> findByQuantityGreaterThan(int quantity);

}
