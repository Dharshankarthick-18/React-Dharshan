package com.examly.springapp.controllers;

import com.examly.springapp.entities.GroceryItem;
import com.examly.springapp.services.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grocery")
public class GroceryItemController {
    private final GroceryItemService groceryItemService;

        @Autowired
            public GroceryItemController(GroceryItemService groceryItemService) {
                    this.groceryItemService = groceryItemService;
                        }

                            //  Get all grocery items
                                @GetMapping
                                    public List<GroceryItem> getAllGroceryItems() {
                                            return groceryItemService.getAllGroceryItems();
                                                }

                                                    //  Get a single grocery item by ID
                                                        @GetMapping("/{id}")
                                                            public GroceryItem getGroceryItemById(@PathVariable Long id) {
                                                                    return groceryItemService.getGroceryItemById(id);
                                                                        }

                                                                            //  Add a new grocery item
                                                                                @PostMapping
                                                                                    public GroceryItem addGroceryItem(@RequestBody GroceryItem groceryItem) {
                                                                                            return groceryItemService.addGroceryItem(groceryItem);
                                                                                                }

                                                                                                    // ✅ Update an existing grocery item
                                                                                                        @PutMapping("/{id}")
                                                                                                            public GroceryItem updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
                                                                                                                    return groceryItemService.updateGroceryItem(id, updatedItem);
                                                                                                                        }

                                                                                                                            // ✅ Delete a grocery item by ID
                                                                                                                                @DeleteMapping("/{id}")
                                                                                                                                    public String deleteGroceryItem(@PathVariable Long id) {
                                                                                                                                            groceryItemService.deleteGroceryItem(id);
                                                                                                                                                    return "Grocery item deleted successfully";
                                                                                                                                                        }
                                                                                                                                                        }
                                                                                                                                                        