package com.example.LoanManagementSystem.Interface;

import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.UserModel;
import org.springframework.http.ResponseEntity;

public interface UserInterface {
    ResponseEntity<?> createUser(UserModel userModel);
    ResponseEntity<?> getUserById(Long userId);
    ResponseEntity<?> updateUser(Long userId, User user);
    void deleteUser(Long userId);
}
