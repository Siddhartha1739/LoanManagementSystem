package com.example.LoanManagementSystem.service;

import com.example.LoanManagementSystem.Conversions.Conversion;
import com.example.LoanManagementSystem.Interface.UserInterface;
import com.example.LoanManagementSystem.dao.UserRepository;
import com.example.LoanManagementSystem.entity.LoanApplication;
import com.example.LoanManagementSystem.entity.Admin;
import com.example.LoanManagementSystem.entity.User;
import com.example.LoanManagementSystem.models.AdminModel;
import com.example.LoanManagementSystem.models.LoanStatus;
import com.example.LoanManagementSystem.models.UserModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
            UserModel userModel=conversion.EntityToModelUser(user.get());
            return new ResponseEntity<>(userModel,HttpStatus.FOUND);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user Id not found");

    }

    @Override
    public ResponseEntity<?> updateUser(Long userId, UserModel user) {
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
    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
         if(user.isPresent()){
             userRepository.deleteById(userId);
             return ResponseEntity.status(HttpStatus.OK).body("User Deleted successfully");
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");

    }

    @Override
    public ResponseEntity<?> UserDetails(){
        List<User> users=userRepository.findAll();
        List userList=new ArrayList();
        try{
            if(!users.isEmpty()){

                users.forEach(user -> {
                    UserModel userModel=conversion.EntityToModelUser(user);
                    for(int i=0;i<user.getLoanApplications().size();i++){
                        if(user.getLoans().get(i).getStatus().equals(LoanStatus.PAID)){
                            userList.add(userModel.getName());
                            userList.add(userModel.getLoanApplications().get(i).getLoanApproval().getApproveStatus());
                            userList.add(userModel.getLoans().get(i).getAmount());
                            userList.add(userModel.getLoans().get(i).getEndDate());
                            userList.add(userModel.getLoans().get(i).getRemainingAmount());
                            userList.add(userModel.getLoans().get(i).getStatus());
                            userList.add(userModel.getLoans().get(i).getPayments());
                        }
                        if(user.getLoans().get(i).getStatus().equals(LoanStatus.ACTIVE)){
                            userList.add(userModel.getName());
                            userList.add(userModel.getLoanApplications().get(i).getLoanApproval().getApproveStatus());
                            userList.add(userModel.getLoans().get(i).getAmount());
                            userList.add(userModel.getLoans().get(i).getEndDate());
                            userList.add(userModel.getLoans().get(i).getRemainingAmount());
                            userList.add(userModel.getLoans().get(i).getStatus());
                            userList.add(userModel.getLoans().get(i).getPayments());
                        }if(user.getLoans().get(i).getStatus().equals(LoanStatus.DEFAULTED)){
                            userList.add(userModel.getName());
                            userList.add(userModel.getLoanApplications().get(i).getLoanApproval().getApproveStatus());
                            userList.add(userModel.getLoans().get(i).getAmount());
                            userList.add(userModel.getLoans().get(i).getEndDate());
                            userList.add(userModel.getLoans().get(i).getRemainingAmount());
                            userList.add(userModel.getLoans().get(i).getStatus());
                            userList.add(userModel.getLoans().get(i).getPayments());
                        }
                       // System.out.println("User name | User Approval Status | Loan Amount | Loan end Date | User Remaining Amount | User Loan Status | User Payments ");
                        //System.out.println(user.getName()+" | "+user.getLoanApplications().get(i).getLoanApproval().getApproveStatus()+" | "+user.getLoans().get(i).getAmount()+" | "+user.getLoans().get(i).getEndDate()+" | "+user.getLoans().get(i).getRemainingAmount()+" | "+user.getLoans().get(i).getStatus()+" | "+user.getLoans().get(i).getPayments());


                    }

                });
                System.out.println(userList);
                return ResponseEntity.status(HttpStatus.OK).body("Data Found");
            }
        }
        catch (Exception ex){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null Pointer Exception");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Found");
    }
}