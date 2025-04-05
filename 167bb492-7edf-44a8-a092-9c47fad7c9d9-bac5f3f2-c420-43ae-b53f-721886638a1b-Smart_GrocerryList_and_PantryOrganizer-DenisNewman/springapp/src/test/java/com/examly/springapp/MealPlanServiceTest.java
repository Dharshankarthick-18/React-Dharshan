package com.examly.springapp;

import com.examly.springapp.controllers.GroceryItemController;
import com.examly.springapp.controllers.RecipeController;
import com.examly.springapp.controllers.UserController;
import com.examly.springapp.entities.GroceryItem;
import com.examly.springapp.entities.MealPlan;
import com.examly.springapp.entities.Recipe;
import com.examly.springapp.entities.User;
import com.examly.springapp.repositories.GroceryItemRepository;
import com.examly.springapp.repositories.MealPlanRepository;
import com.examly.springapp.repositories.RecipeRepository;
import com.examly.springapp.repositories.UserRepository;
import com.examly.springapp.services.GroceryItemService;
import com.examly.springapp.services.MealPlanService;
import com.examly.springapp.services.RecipeService;
import com.examly.springapp.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MealPlanServiceTest {

    


    @Autowired
    private MealPlanService mealPlanService;

    @MockBean
    private MealPlanRepository mealPlanRepository;

 
@Autowired
    private GroceryItemRepository groceryItemRepository;

  
    @Autowired
    private RecipeRepository recipeRepository;

   

    @Autowired
    private UserRepository userRepository;
    private MealPlan mealPlan;
    private User user;
    private Recipe recipe;

    private static final String LOG_FOLDER_PATH = "logs";
    private static final String LOG_FILE_PATH = "logs/application.log"; 

    

    @BeforeEach
    void setUp() {
        // Setup dummy data
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pasta");
        //recipe.setIngredients("Flour, Water, Salt");

        mealPlan = new MealPlan();
        mealPlan.setId(1L);
        mealPlan.setDay("Monday");
        mealPlan.setMealType("Lunch");
        mealPlan.setUser(user);
        mealPlan.setRecipe(recipe);

       
   // recipe.setIngredients("Example Ingredients");
    mealPlan.setRecipe(recipe);  // 
    groceryItemRepository.deleteAll();
    recipeRepository.deleteAll();
    userRepository.deleteAll();   

    recipeRepository.deleteAll();

        // Add some test data
        Recipe recipe1 = new Recipe();
        recipe1.setName("Pasta");
       // recipe1.setIngredients("Noodles, Sauce");
        recipe1.setInstructions("Boil noodles, add sauce.");
        recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Pasta");
       // recipe2.setIngredients("Whole wheat noodles, Pesto");
        recipe2.setInstructions("Cook noodles, mix pesto.");
        recipeRepository.save(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setName("Salad");
       // recipe3.setIngredients("Lettuce, Tomato, Dressing");
        recipe3.setInstructions("Chop and mix ingredients.");
        recipeRepository.save(recipe3);

        groceryItemRepository.deleteAll();

        // Add test data
        GroceryItem item1 = new GroceryItem();
        item1.setName("Milk");
        item1.setCategory("Dairy");
        item1.setQuantity(2);
        groceryItemRepository.save(item1);

        GroceryItem item2 = new GroceryItem();
        item2.setName("Cheese");
        item2.setCategory("Dairy");
        item2.setQuantity(1);
        groceryItemRepository.save(item2);

        GroceryItem item3 = new GroceryItem();
        item3.setName("Apples");
        item3.setCategory("Fruits");
        item3.setQuantity(5);
        groceryItemRepository.save(item3);

       
}

@Test
public void Annotation_testIdAnnotation() {
    GroceryItem groceryItem = new GroceryItem();
    groceryItem.setId(1L);

    // Verify that the ID is set correctly
    assertNotNull(groceryItem.getId(), "ID should not be null");
    assertEquals(1L, groceryItem.getId(), "ID should be 1");
}
@Test
public void Annotation_testLombokAnnotations() {
    GroceryItem groceryItem = new GroceryItem();
    groceryItem.setId(1L);
    groceryItem.setName("Apple");
    groceryItem.setCategory("Fruit");
    groceryItem.setQuantity(10);
    groceryItem.setUnit("Kg");

    // Testing Lombok's generated getter methods
    assertEquals(1L, groceryItem.getId(), "The ID should be 1");
    assertEquals("Apple", groceryItem.getName(), "The name should be 'Apple'");
    assertEquals("Fruit", groceryItem.getCategory(), "The category should be 'Fruit'");
    assertEquals(10, groceryItem.getQuantity(), "The quantity should be 10");
    assertEquals("Kg", groceryItem.getUnit(), "The unit should be 'Kg'");
}

@Test
public void Annotation_testJoinColumnMapping() {
    // Create a new User
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");

    // Create a new GroceryItem and associate it with the User
    GroceryItem groceryItem = new GroceryItem();
    groceryItem.setId(1L);
    groceryItem.setName("Apple");
    groceryItem.setCategory("Fruit");
    groceryItem.setQuantity(10);
    groceryItem.setUnit("Kg");
    groceryItem.setUser(user); // Set the user using @JoinColumn

    // Check that the grocery item is correctly mapped to the user
    assertNotNull(groceryItem.getUser(), "User should be mapped to the GroceryItem");
    assertEquals(1L, groceryItem.getUser().getId(), "The User ID should be 1");
    assertEquals("John Doe", groceryItem.getUser().getName(), "The User name should be 'John Doe'");
    assertEquals("Apple", groceryItem.getName(), "The GroceryItem name should be 'Apple'");
}
@Test
    public void Repository_testUserRepositoryExists() {
        assertNotNull(userRepository);  // Check if UserRepository is available
    }

    @Test
    public void Repository_testMealPlanRepository() {
        assertNotNull(mealPlanRepository);  // Check if UserRepository is available
    }

    @Test
    public void Repository_testGroceryItemRepository() {
        assertNotNull(groceryItemRepository);  // Check if UserRepository is available
    }
    @Test
    public void Repository_testRecipeRepository() {
        assertNotNull(recipeRepository);  // Check if UserRepository is available
    }




    @Test
    void CRUD_testSaveMealPlan() {
        when(mealPlanRepository.save(mealPlan)).thenReturn(mealPlan);

        MealPlan savedMealPlan = mealPlanService.saveMealPlan(mealPlan);

        assertThat(savedMealPlan).isNotNull();
        assertThat(savedMealPlan.getDay()).isEqualTo("Monday");
        assertThat(savedMealPlan.getMealType()).isEqualTo("Lunch");
    }

    @Test
    void CRUD_testGetMealPlanById() {
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));

        Optional<MealPlan> fetchedMealPlan = mealPlanService.getMealPlan(1L);

        assertThat(fetchedMealPlan).isPresent();
        assertThat(fetchedMealPlan.get().getId()).isEqualTo(1L);
        assertThat(fetchedMealPlan.get().getDay()).isEqualTo("Monday");
    }

    @Test
    void CRUD_testGetMealPlanById_NotFound() {
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<MealPlan> fetchedMealPlan = mealPlanService.getMealPlan(1L);

        assertThat(fetchedMealPlan).isEmpty();
    }

    
    @Test
    void CRUD_testDeleteMealPlan() {
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(mealPlan));
        doNothing().when(mealPlanRepository).deleteById(1L);

        mealPlanService.deleteMealPlan(1L);

        verify(mealPlanRepository, times(1)).deleteById(1L);
    }

    // Tests for GroceryItem
    @Test
    public void CRUD_testCreateGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName("Apple");
        groceryItem.setQuantity(10);
        GroceryItem savedItem = groceryItemRepository.save(groceryItem);
        assertNotNull(savedItem.getId());
    }

    @Test
    public void CRUD_testReadGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName("Banana");
        groceryItem.setQuantity(20);
        GroceryItem savedItem = groceryItemRepository.save(groceryItem);

        Optional<GroceryItem> fetchedItem = groceryItemRepository.findById(savedItem.getId());
        assertTrue(fetchedItem.isPresent());
        assertEquals("Banana", fetchedItem.get().getName());
    }

    @Test
    public void CRUD_testUpdateGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName("Orange");
        groceryItem.setQuantity(15);
        GroceryItem savedItem = groceryItemRepository.save(groceryItem);

        savedItem.setQuantity(25);
        GroceryItem updatedItem = groceryItemRepository.save(savedItem);
        assertEquals(25, updatedItem.getQuantity());
    }

    @Test
    public void CRUD_testDeleteGroceryItem() {
        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName("Grapes");
        groceryItem.setQuantity(30);
        GroceryItem savedItem = groceryItemRepository.save(groceryItem);

        groceryItemRepository.deleteById(savedItem.getId());
        assertFalse(groceryItemRepository.findById(savedItem.getId()).isPresent());
    }

    // Tests for Recipe
    @Test
    public void CRUD_testCreateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Pancakes");
       // recipe.setIngredients("Flour, Eggs, Milk");
        recipe.setInstructions("Mix ingredients and cook on skillet.");
        Recipe savedRecipe = recipeRepository.save(recipe);
        assertNotNull(savedRecipe.getId());
    }

    @Test
    public void CRUD_testReadRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Omelette");
        //recipe.setIngredients("Eggs, Salt, Pepper");
        recipe.setInstructions("Beat eggs and cook in a pan.");
        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Recipe> fetchedRecipe = recipeRepository.findById(savedRecipe.getId());
        assertTrue(fetchedRecipe.isPresent());
        assertEquals("Omelette", fetchedRecipe.get().getName());
    }

    @Test
    public void CRUD_testUpdateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Salad");
        //recipe.setIngredients("Lettuce, Tomatoes, Cucumber");
        recipe.setInstructions("Mix all ingredients in a bowl.");
        Recipe savedRecipe = recipeRepository.save(recipe);

        savedRecipe.setInstructions("Chop and mix ingredients.");
        Recipe updatedRecipe = recipeRepository.save(savedRecipe);
        assertEquals("Chop and mix ingredients.", updatedRecipe.getInstructions());
    }

    @Test
    public void CRUD_testDeleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Soup");
        //recipe.setIngredients("Vegetables, Stock, Spices");
        recipe.setInstructions("Cook all ingredients together.");
        Recipe savedRecipe = recipeRepository.save(recipe);

        recipeRepository.deleteById(savedRecipe.getId());
        assertFalse(recipeRepository.findById(savedRecipe.getId()).isPresent());
    }

    // Tests for User
    @Test
    public void CRUD_testCreateUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
    }

    @Test
    public void CRUD_testReadUser() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("mypassword");
        User savedUser = userRepository.save(user);

        Optional<User> fetchedUser = userRepository.findById(savedUser.getId());
        assertTrue(fetchedUser.isPresent());
        assertEquals("Jane Doe", fetchedUser.get().getName());
    }

    @Test
    public void CRUD_testUpdateUser() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("alicepass");
        User savedUser = userRepository.save(user);

        savedUser.setPassword("newalicepass");
        User updatedUser = userRepository.save(savedUser);
        assertEquals("newalicepass", updatedUser.getPassword());
    }

    @Test
    public void CRUD_testDeleteUser() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setPassword("bobpass");
        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }
    @Test
    public void JPQL_testFindRecipesByName() {
        List<Recipe> pastaRecipes = recipeRepository.findRecipesByName("Pasta");
        assertEquals(2, pastaRecipes.size());

        Recipe firstRecipe = pastaRecipes.get(0);
        assertEquals("Pasta", firstRecipe.getName());
       // assertNotNull(firstRecipe.getIngredients());
    }

    @Test
    public void JPQL_testFindRecipesByNameNoMatch() {
        List<Recipe> nonExistentRecipes = recipeRepository.findRecipesByName("Burger");
        assertTrue(nonExistentRecipes.isEmpty());
    }

    @Test
    public void JPQL_testFindByCategory() {
        List<GroceryItem> dairyItems = groceryItemRepository.findByCategory("Dairy");
        assertEquals(2, dairyItems.size());

        GroceryItem firstItem = dairyItems.get(0);
        assertEquals("Dairy", firstItem.getCategory());
        assertNotNull(firstItem.getName());
    }

    @Test
    public void JPQL_testFindByCategoryNoMatch() {
        List<GroceryItem> nonExistentCategoryItems = groceryItemRepository.findByCategory("Bakery");
        assertTrue(nonExistentCategoryItems.isEmpty());
    }

    @Test
public void PaginateSorting_testGetMealPlansSortedWithInvalidSortDir() {
    // Prepare test data
    MealPlan mealPlan1 = new MealPlan();
    mealPlan1.setId(1L);
    mealPlan1.setDay("Monday");

    MealPlan mealPlan2 = new MealPlan();
    mealPlan2.setId(2L);
    mealPlan2.setDay("Tuesday");

    List<MealPlan> mealPlans = Arrays.asList(mealPlan1, mealPlan2);

    // Mock the repository method for invalid sort direction (defaults to ascending)
    when(mealPlanRepository.findAll(Sort.by(Sort.Order.asc("day")))).thenReturn(mealPlans);

    // Test with invalid sort direction
    List<MealPlan> sortedMealPlans = mealPlanService.getAllMealPlansSorted("day");

    // Assertions
    assertNotNull(sortedMealPlans, "Sorted meal plans should not be null");
    assertEquals(2, sortedMealPlans.size(), "Expected 2 meal plans in the sorted list");
    assertEquals("Monday", sortedMealPlans.get(0).getDay(), "First meal plan day should be Monday");
    assertEquals("Tuesday", sortedMealPlans.get(1).getDay(), "Second meal plan day should be Tuesday");
}
    
@Test
public void PaginateSorting_testGetMealPlansSortedDescending() {
    // Prepare test data
    MealPlan mealPlan1 = new MealPlan();
    mealPlan1.setId(1L);
    mealPlan1.setDay("Monday");

    MealPlan mealPlan2 = new MealPlan();
    mealPlan2.setId(2L);
    mealPlan2.setDay("Tuesday");

    // Prepare the sorted list in descending order (Tuesday comes before Monday)
    List<MealPlan> mealPlans = Arrays.asList(mealPlan2, mealPlan1);

    // Mock the repository method for descending sort
    when(mealPlanRepository.findAll(Sort.by(Sort.Order.desc("day")))).thenReturn(mealPlans);

    // Test with descending sort direction
    List<MealPlan> sortedMealPlans = mealPlanService.getAllMealPlansSorted("day");

    // Print the results for verification
    System.out.println("Sorted Meal Plans in Descending Order:");
    sortedMealPlans.forEach(mealPlan -> 
        System.out.println("ID: " + mealPlan.getId() + ", Day: " + mealPlan.getDay()));
}

@Test
public void PaginateSorting_testGetMealPlansWithPagination() {
    // Prepare test data
    MealPlan mealPlan1 = new MealPlan();
    mealPlan1.setId(1L);
    mealPlan1.setDay("Monday");

    MealPlan mealPlan2 = new MealPlan();
    mealPlan2.setId(2L);
    mealPlan2.setDay("Tuesday");

    MealPlan mealPlan3 = new MealPlan();
    mealPlan3.setId(3L);
    mealPlan3.setDay("Wednesday");

    // Mock a Page object
    Page<MealPlan> page = new PageImpl<>(Arrays.asList(mealPlan1, mealPlan2));

    // Mock the repository method for pagination
    when(mealPlanRepository.findAll(PageRequest.of(0, 2))).thenReturn(page);

    // Test with pagination (page = 0, size = 2)
    Page<MealPlan> paginatedMealPlans = mealPlanService.getMealPlansWithPagination(0, 2);

    // Print the results for verification
    System.out.println("Paginated Meal Plans (Page 0, Size 2):");
    paginatedMealPlans.getContent().forEach(mealPlan -> 
        System.out.println("ID: " + mealPlan.getId() + ", Day: " + mealPlan.getDay()));
}
@Test
    public void Mapping_testOneToManyMapping() {
        // Create MealPlan objects
        MealPlan mealPlan1 = new MealPlan();
        mealPlan1.setId(1L);
        mealPlan1.setDay("Monday");
        
        MealPlan mealPlan2 = new MealPlan();
        mealPlan2.setId(2L);
        mealPlan2.setDay("Tuesday");

        // Create Recipe and associate with MealPlans
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Pasta");
        recipe.setInstructions("Boil water and cook pasta");
        recipe.setMealPlans(Arrays.asList(mealPlan1, mealPlan2));  // Set the mealPlans list

        // Print out the Recipe details and associated MealPlans
        System.out.println("Recipe: " + recipe.getName());
        System.out.println("Meal Plans associated with Recipe:");
        for (MealPlan mealPlan : recipe.getMealPlans()) {
            System.out.println("Meal Plan ID: " + mealPlan.getId() + ", Day: " + mealPlan.getDay());
        }

        // Print MealPlan details
        System.out.println("MealPlan1 associated with Recipe ");
        //System.out.println("MealPlan2 associated with Recipe: " + mealPlan2.getRecipe().getName());
    }

@Test
    public void Mapping_testManyToOneMapping() {
        GroceryItem groceryItem = new GroceryItem();
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        groceryItem.setId(1L);
        groceryItem.setName("Apple");
        groceryItem.setCategory("Fruit");
        groceryItem.setQuantity(10);
        groceryItem.setUnit("Kg");
        groceryItem.setUser(user);

        assertNotNull(groceryItem.getUser(), "User should be mapped to the GroceryItem");
        assertEquals(1L, groceryItem.getUser().getId(), "The User ID should be 1");
        assertEquals("John Doe", groceryItem.getUser().getName(), "The User name should be 'John Doe'");
    }
   
    @Test 
    public void Swagger_testConfigurationFolder() { 
        String directoryPath = "src/main/java/com/examly/springapp/configuration"; // Replace with the path to your directory 
        File directory = new File(directoryPath); 
        assertTrue(directory.exists() && directory.isDirectory()); 
    }
    
    @Test

	public void Swagger_testConfigFile() {

		String filePath = "src/main/java/com/examly/springapp/configuration/SwaggerConfig.java";

		// Replace with the path to your file

		File file = new File(filePath);

		assertTrue(file.exists() && file.isFile());

	}
    private void checkAnnotationExists(String className, String annotationName) {
		try {
			Class<?> clazz = Class.forName(className);
			ClassLoader classLoader = clazz.getClassLoader();
			Class<?> annotationClass = Class.forName(annotationName, false, classLoader);
			assertNotNull(clazz.getAnnotation((Class) annotationClass)); // Use raw type
		} catch (ClassNotFoundException | NullPointerException e) {
			fail("Class " + className + " or annotation " + annotationName + " does not exist.");
		}
	}

    @Test
	   public void Swagger_testConfigHasAnnotation() {
	       checkAnnotationExists("com.examly.springapp.configuration.SwaggerConfig", "org.springframework.context.annotation.Configuration");
	   }
	 
	
       @Test
       public void Log_testLogFolderAndFileCreation() {
           // Check if the "logs" folder exists
           File logFolder = new File(LOG_FOLDER_PATH);
           assertTrue(logFolder.exists(), "Log folder should be created");
   
           // Check if the "application.log" file exists inside the "logs" folder
           File logFile = new File(LOG_FILE_PATH);
           assertTrue(logFile.exists(), "Log file should be created inside 'logs' folder");
       }
       @Autowired
       private UserService userService;

       @Test
    void AOP_testAOPLoggingAspect() {
        // Call the service method
        userService.getAllUsers();

        // The aspect should log before method execution
        System.out.println("Testing AOP: The logging aspect should execute before the service method.");
    }
    @Autowired
    private GroceryItemService groceryService;

    @Test
      void AOP_testAOPLoggingServiceAspect()
      {
        groceryService.getAllGroceryItems();
        System.out.println("Testing AOP: The logging aspect should execute before the service method.");

      }
      @Autowired
      private UserController userController;

      @Test
      void AOP_testAOPLoggingControllerAspect() {
          // Call the service method
          userController.getAllUsers();
  
          // The aspect should log before method execution
          System.out.println("Testing AOP: The logging aspect should execute before the service method.");
      }

      @Autowired
      private GroceryItemController itemController;

      @Test
      void AOP_testAOPLoggingControllerGroceryAspect() {
          // Call the service method
          itemController.getAllGroceryItems();
  
          // The aspect should log before method execution
          System.out.println("Testing AOP: The logging aspect should execute before the service method.");
      }


}
    
  

     

