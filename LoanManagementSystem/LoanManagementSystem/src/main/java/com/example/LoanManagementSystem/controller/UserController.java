package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.models.UserModel;
import com.example.LoanManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserModel userModel) {
        return userService.createUser(userModel);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestParam("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/put")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

