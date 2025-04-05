package com.examly.springapp.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "app_users")
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;

@Column(unique = true, nullable = false)
private String email;

@JsonIgnore
private String password;  // Store hashed password, not plain text

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
@JsonIgnore
private List<GroceryItem> groceryItems;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
@JsonIgnore
private List<MealPlan> mealPlans;
}
