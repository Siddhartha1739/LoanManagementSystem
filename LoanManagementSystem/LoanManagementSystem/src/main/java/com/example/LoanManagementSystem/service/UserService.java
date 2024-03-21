package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.UserInterface;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.models.UserModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Conversion conversion;

    @Override
    public ResponseEntity<?> createUser(UserModel userModel) {
        Long userID=userModel.getUserId();
        if(!userRepository.existsById(userID)){
            User user=conversion.ModelToEntityUser(userModel);
            userRepository.save(user);
            return new ResponseEntity<>("User added Successfully", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already exist");
    }

    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        Optional<User> user=userRepository.findById(userId);
        if (user.isPresent()){
            UserModel userModel=conversion.EntityToModelUser(userRepository.findById(userId).orElse(null));
            return new ResponseEntity<>(userModel,HttpStatus.FOUND);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user Id not found");

    }

    @Override
    public ResponseEntity<?> updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null && user!=null) {

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setAddress(user.getAddress());
            return new ResponseEntity<>(userRepository.save(existingUser),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Id not found");
    }
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(userId);
    }
}