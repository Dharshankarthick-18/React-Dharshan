package com.examly.springapp.services;

import com.examly.springapp.entities.GroceryItem;
import com.examly.springapp.repositories.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemService {
private final GroceryItemRepository groceryItemRepository;

@Autowired
public GroceryItemService(GroceryItemRepository groceryItemRepository) {
this.groceryItemRepository = groceryItemRepository;
}


public List<GroceryItem> getAllGroceryItems() {
return groceryItemRepository.findAll();
}


public GroceryItem getGroceryItemById(Long id) {
return groceryItemRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Grocery item not found"));
}


public GroceryItem addGroceryItem(GroceryItem item) {
return groceryItemRepository.save(item);
}


public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
GroceryItem existingItem = getGroceryItemById(id);
existingItem.setName(updatedItem.getName());
existingItem.setCategory(updatedItem.getCategory());
existingItem.setQuantity(updatedItem.getQuantity());
existingItem.setUnit(updatedItem.getUnit());
return groceryItemRepository.save(existingItem);
}

// ✅ Delete a grocery item by ID
public void deleteGroceryItem(Long id) {
groceryItemRepository.deleteById(id);
}
}
