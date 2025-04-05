package com.examly.springapp.services;

import com.examly.springapp.entities.User;
import com.examly.springapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;

public List<User> getAllUsers() {
return userRepository.findAll();
}

public Optional<User> getUserById(Long id) {
return userRepository.findById(id);
}

public Optional<User> getUserByEmail(String email) {
return userRepository.findByEmail(email);
}

public User saveUser(User user) {
if (userRepository.existsByEmail(user.getEmail())) {
throw new IllegalArgumentException("Email already exists");
}
return userRepository.save(user);
}

public void deleteUser(Long id) {
userRepository.deleteById(id);
}
}
