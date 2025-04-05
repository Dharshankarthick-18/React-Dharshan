package com.examly.springapp.controllers;

import com.examly.springapp.entities.User;
import com.examly.springapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

@Autowired
private UserService userService;

@GetMapping
public List<User> getAllUsers() {
return userService.getAllUsers();
}

@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable Long id) {
Optional<User> user = userService.getUserById(id);
return user.map(ResponseEntity::ok)
.orElseGet(() -> ResponseEntity.notFound().build());
}

@GetMapping("/email")
public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
Optional<User> user = userService.getUserByEmail(email);
return user.map(ResponseEntity::ok)
.orElseGet(() -> ResponseEntity.notFound().build());
}

@PostMapping
public ResponseEntity<?> createUser(@RequestBody User user) {
try {
User savedUser = userService.saveUser(user);
return ResponseEntity.ok(savedUser);
} catch (IllegalArgumentException e) {
return ResponseEntity.badRequest().body(e.getMessage());
}
}

@PutMapping("/{id}")
public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
Optional<User> existingUser = userService.getUserById(id);
if (existingUser.isPresent()) {
user.setId(id);
return ResponseEntity.ok(userService.saveUser(user));
} else {
return ResponseEntity.notFound().build();
}
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
userService.deleteUser(id);
return ResponseEntity.ok().build();
}
}
