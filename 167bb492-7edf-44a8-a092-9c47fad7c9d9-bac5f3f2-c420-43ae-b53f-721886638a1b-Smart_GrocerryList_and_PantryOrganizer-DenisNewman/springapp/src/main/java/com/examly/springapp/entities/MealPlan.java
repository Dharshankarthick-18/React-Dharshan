package com.examly.springapp.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class MealPlan {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "meal_day")
private String day;

private String mealType;

@ManyToOne(cascade = CascadeType.ALL)
@JsonIgnore
@JoinColumn(name = "user_id")
private User user;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "recipe_id")
private Recipe recipe;
}
