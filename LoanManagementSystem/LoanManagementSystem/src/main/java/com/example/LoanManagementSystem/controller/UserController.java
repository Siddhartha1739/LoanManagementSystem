package com.example.LoanManagementSystem.controller;

import com.example.LoanManagementSystem.Interface.UserInterface;
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
    UserInterface userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserModel userModel) {
        return userService.createUser(userModel);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(@RequestParam("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody UserModel user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/GetUserDetails")
    public ResponseEntity<?> UserDetails() {
        return userService.UserDetails();
    }

}

