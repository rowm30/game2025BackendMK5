package com.game2025backendMK5.game2025backendMK5.controller;

import com.game2025backendMK5.game2025backendMK5.model.User;
import com.game2025backendMK5.game2025backendMK5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")// Allows cross-origin requests from all origins
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;


    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        try {
            User user = userService.createUser(newUser);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    // Optionally add other CRUD operations here as needed
}